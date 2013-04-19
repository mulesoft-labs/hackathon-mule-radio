/*
 * Statis variable definitions.
 */
var ENDPOINT_SONG_LIST 		= "http://172.16.20.142:9090/client/songs";
var ENDPOINT_SONG_SEARCH 	= "http://172.16.20.142:9090/client/find";

var MSG_SYSTEM_CRASH 		= "The system cannot respond properly. Please wait a few seconds and retry...";
var MSG_NO_RESULTS			= "There are no songs availables in the site yet. Let's wait for more Agents to come";
var MSG_NO_SEARCH_RESULTS	= "There are no songs that match with the search";

var ERROR_SYSTEM_CRASH		= 1;
var ERROR_NO_RESULTS		= 2;
var ERROR_NO_SEARCH_RESULTS	= 3;

/*
 * Generate an HTML element <p> that has an error message.
 * The message is defined in static variables and there is one error message per error code
 */
function createErrorHtml(code) {
	var errorMsg = document.createElement("p");
	errorMsg.setAttribute("class", "warning");
	if (code == ERROR_NO_SEARCH_RESULTS) {
		errorMsg.innerHTML = MSG_NO_SEARCH_RESULTS;
	} else if (code == ERROR_NO_RESULTS) {
		errorMsg.innerHTML = MSG_NO_RESULTS;
	} else {
		errorMsg.innerHTML = MSG_SYSTEM_CRASH;
	}
	
	return errorMsg;
}