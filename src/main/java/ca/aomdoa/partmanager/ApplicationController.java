package ca.aomdoa.partmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.aomdoa.partmanager.domain.BinaryFile;
import ca.aomdoa.partmanager.domain.PartBin;
import ca.aomdoa.partmanager.repository.BinaryFileRepository;
import ca.aomdoa.partmanager.repository.FootprintRepository;
import ca.aomdoa.partmanager.repository.PartBinRepository;

@Controller
public class ApplicationController {
	private static final Log logger = LogFactory.getLog(ApplicationController.class);
	@Autowired
	FootprintRepository footprintRepo;
	@Autowired
	PartBinRepository binRepo;
	@Autowired
	BinaryFileRepository fileRepo;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("footprints",  footprintRepo.findAll());
		Collection<String> locations = new ArrayList<String>();
		for(PartBin bin : binRepo.findAll()) { //How to get the list unique location names? then this could be ajax
			if(!locations.contains(bin.getLocation())) {
				locations.add(bin.getLocation());
			}
		}
		model.addAttribute("locations", locations);
		return "index";
	}
	
	@RequestMapping("/retailers")
	public String retailers() {
		logger.debug("retailers request");
		return "retailers";
	}
	
	@RequestMapping("/footprints")
	public String footprints() {
		logger.debug("footprints request");
		return "footprints";
	}
	
	@RequestMapping("/bins")
	public String bins() {
		logger.debug("bins request");
		return "bins";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/bins",method=RequestMethod.POST)
	public ResponseEntity<String> createBins(@RequestParam(required=true) String location, @RequestParam(required=true) String format, @RequestParam(required=true) Long numBins) {
		logger.debug("createBins, location = " + location + ", form = " + format + ", numBins = " + numBins);
		if(!format.matches("^\\d\\d\\d\\d\\d$")) {
			return new ResponseEntity<String>("Format must be five numbers", HttpStatus.BAD_REQUEST);
		}
		Long form = Long.valueOf(format);
		for(int i=1; i<=numBins; i++) {
			PartBin bin = new PartBin();
			bin.setBin(String.format("%05d", (form + i)));
			bin.setLocation(location);
			binRepo.save(bin);
		}
		return new ResponseEntity<String>(numBins.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/bins",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteBins(@RequestParam(required=true) String location) {
		Integer numDeleted = binRepo.deleteByLocation(location);
		return new ResponseEntity<String>(numDeleted.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/document",method=RequestMethod.GET)
	public void getFile(@RequestParam(required=true) Long partDocumentId, HttpServletResponse resp) throws IOException {
		BinaryFile file = fileRepo.getDocumentFile(partDocumentId);
		resp.setHeader("Content-Disposition", "filename=" + file.getFilename());
		resp.setContentLength(file.getData().length);
		resp.getOutputStream().write(file.getData());
	}
}
