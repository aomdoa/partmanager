function processDataResponse(data) {
	if(data.status == 200 || data.status == 201 || data.status == 204) {
		return true;
	} else if(data.status == 400 && data.responseJSON.errors) {
		window.alert(data.responseJSON.errors[0].message);
	} else {
		window.alert(data.status + " - " + data.statusText + "\n" + data.responseText);
	}
	return false;
}

function processTableResults(data, callback, baseUrl, dataName) {
	var url = getDataUrl(data, baseUrl);
	var draw = data.draw;
	$.ajax(url).success(function(data) {
		callback(getData(data, draw, dataName));
	});
}

function getDataUrl(data, baseUrl) {
	var column = data.columns[data.order[0].column].data;
	var direction = data.order[0].dir;
	return baseUrl + '?size=' + data.length + '&page=' + (data.start / data.length) + '&sort=' + column + ',' + direction;
}

function getData(data, drawIndex, dataName) {
	if(data._embedded) {
		return {
			draw: drawIndex,
			recordsTotal: data.page.totalElements,
			recordsFiltered: data.page.totalElements,
			data: data._embedded[dataName]
		};
	} else {
		return {
			draw: drawIndex,
			recordsTotal: 0,
			recordsFiltered: 0,
			data: []
		};
	}
}