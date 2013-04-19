$(document).ready(function(){
	
	var stream = {
		title: 	"Im the title",
		mp3: 	""
	},
	ready = false;
	
	$("#jquery_jplayer_1").jPlayer({
		ready: function (event) {
			ready = true;
			$(this).jPlayer("setMedia", stream);
		},
		pause: function() {
			$(this).jPlayer("clearMedia");
		},
		error: function(event) {
			if(ready && event.jPlayer.error.type === $.jPlayer.error.URL_NOT_SET) {
				// Setup the media stream again and play it.
				$(this).jPlayer("setMedia", stream).jPlayer("play");
			}
		},
		swfPath: "js",
		supplied: "mp3",
		preload: "none",
		wmode: "window"
	});
	
	loadSongs();
});

/*
 * Select one song and make the player play it
 */
function playSong(url, songHtml) {
	$("#jquery_jplayer_1").jPlayer("setMedia", {mp3: url}).jPlayer("play");
	var songObject = songHtml['attributes']['song'];
	
	var nowPlayingHolder = $('#songplaying');
	nowPlayingHolder.empty();
	nowPlayingHolder.append(createSongHtml(songObject, {skipOnClick: true}));
}

/*
 * 
 */
function logIntoConsole(message) {
	if( console && console.log ) {
		console.log(message);
	}
}

/*
 * Execute the operations to load the songs list
 */
function loadSongs() {
	
	var songsholder = $("#songsholder");
	
	$.ajax({
		url: ENDPOINT_SONG_LIST,
		cache: false
	}).done(function ( data, textStatus, ajaxComponent) {
		logIntoConsole("SUCCESS: " + ajaxComponent);
		logIntoConsole("Status: " + textStatus);
		
		if ( data == null || data == '' || data.length <= 0) {
			logIntoConsole("Data is empty");
					
			songsholder.empty();
			songsholder.append(createErrorHtml(ERROR_NO_RESULTS));
		} else {
			songsholder.empty();
			loadSongList(songsholder, data);
		}
		
	}).fail(function ( ajaxComponent, textStatus, errorThrown) {
		songsholder.empty();
		songsholder.append(createErrorHtml(ERROR_SYSTEM_CRASH));
		
		logIntoConsole("FAIL: " + ajaxComponent);
		logIntoConsole("Status: " + textStatus);
		logIntoConsole("ErrorThrown: " + errorThrown);		
	});
}

/*
 * Execute search is fired from a form submit, so first we need to cancel the event to prevent page reload and then execute the ajax operation.
 * Using a submit instead of a button allows to use ENTER in the textfield and click on button for action
 */
function executeSearch(event) {
	event.cancel = true;
	event.preventDefault();
	searchSongs($("#q").val());
}

/*
 * Execute the operation to search for a song
 */
function searchSongs(query) {
	var songsholder = $("#songsholder");
	
	var queryParams = {"q": query};
	
	$.ajax({
		url: ENDPOINT_SONG_SEARCH,
		data: queryParams,
		cache: false
	}).done(function ( data, textStatus, ajaxComponent) {
		logIntoConsole("SUCCESS: " + ajaxComponent);
		logIntoConsole("Status: " + textStatus);
		
		if ( data == null || data == '' || data.length <= 0) {
			logIntoConsole("Data is empty");
					
			songsholder.empty();
			songsholder.append(createErrorHtml(ERROR_NO_SEARCH_RESULTS));
		} else {
			songsholder.empty();
			loadSongList(songsholder, data);
		}
		
	}).fail(function ( ajaxComponent, textStatus, errorThrown) {
		songsholder.empty();
		songsholder.append(createErrorHtml(ERROR_SYSTEM_CRASH));
		
		logIntoConsole("FAIL: " + ajaxComponent);
		logIntoConsole("Status: " + textStatus);
		logIntoConsole("ErrorThrown: " + errorThrown);		
	});
}

function loadSongList(songsholder, songsList) {
	for (var i = 0, iMax = songsList.length ; i < iMax ; i++) {
		songsholder.append(createSongHtml(songsList[i]));
	}
}

/*
 * Generate an HTML element <div> that has the info of a song.
 */
function createSongHtml(songObject, params) {
	
	var divSong = document.createElement("div");
	
	
	if (params && params.skipOnClick == true) {
		divSong.setAttribute("class", "message song");
	} else {
		divSong.setAttribute("class", "message song");
		divSong.setAttribute("onclick", "javascript:playSong('" + songObject['fileUrl'] + "', this);");
	}
	
	var e = document.createElement("p");
	e.setAttribute("class", "song");
	e.innerHTML = songObject['song'];
	divSong.appendChild(e);
	
	e = document.createElement("p");
	e.setAttribute("class", "artist");
	e.innerHTML = songObject['artist'];
	divSong.appendChild(e);
	
	e = document.createElement("p");
	e.setAttribute("class", "album");
	e.innerHTML = songObject['album'];
	divSong.appendChild(e);
	
	e = document.createElement("p");
	e.setAttribute("class", "category");
	e.innerHTML = songObject['category'];
	divSong.appendChild(e);
	
	// Save the song in a property for later use in the "now playing"
	divSong.attributes['song'] = songObject;
	
	return divSong;
}