package org.mule.hackathon.indexer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.mule.hackathon.agent.model.AgentIndexRecord;
import org.mule.hackathon.indexer.util.Utils;

import com.google.gson.Gson;

public class Indexer implements MusicIndexer {

	private final static Logger LOGGER = Logger.getLogger(Indexer.class
			.getName());

	static String temp = "";
	static int id = 1;
	private HashMap<String, AgentIndexRecord> mp3Index;
	private String baseUri;
	private static Gson gson = new Gson();

	public Indexer() {
		mp3Index = new HashMap<String, AgentIndexRecord>();
		baseUri = "";
	}

	private void listFilesForFolder(final File folder) {

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				if (fileEntry.isFile()) {
					temp = fileEntry.getName();
					if (!temp.contains("."))
						continue;
					if ((temp.substring(temp.lastIndexOf('.') + 1,
							temp.length()).toLowerCase()).equals("mp3")) {
						this.indexFile(fileEntry);
					}
				}
			}
		}
	}

	public void indexFolder(String uri) {
		File folder = new File(uri);

		if (!folder.exists())
			throw new RuntimeException("Folder does not exist");
		System.out.println("Reading files under the folder "
				+ folder.getAbsolutePath());
		listFilesForFolder(folder);
	}

	public AgentIndexRecord find(String fileKey) {
		if (!contains(fileKey))
			throw new RuntimeException("No fie for key:" + fileKey);
		return this.mp3Index.get(fileKey);
	}

	public void indexFile(File file) {
		MP3File f;
		try {
			f = (MP3File) AudioFileIO.read(file);
			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
			audioHeader.getTrackLength();
			audioHeader.getSampleRateAsNumber();
			audioHeader.getChannels();
			audioHeader.isVariableBitRate();

			ID3v1Tag tag = f.getID3v1Tag();
			if (tag != null) {
				AgentIndexRecord item = Utils.toAgentIndex(tag,
						Integer.toString(id), baseUri + id,
						file.getAbsolutePath());
				this.mp3Index.put(Integer.toString(id), item);
				id++;
			} else {
				LOGGER.info("Could not get ID3v1Tag for:"
						+ file.getAbsolutePath());
			}

		} catch (Exception e) {
			LOGGER.severe("Could not get indo from file. Error:"
					+ e.getMessage());
		}

	}

	public void setBaseUri(String uri) {
		baseUri = uri;
	}

	public String getBaseUri(String uri) {
		return baseUri;
	}

	public boolean contains(String fileKey) {
		return mp3Index.containsKey(fileKey);
	}

	public String toJson() {
		return gson.toJson(mp3Index.values());
	}

	public List<AgentIndexRecord> getAgentIndexRecordList() {
		return (List<AgentIndexRecord>) new ArrayList<AgentIndexRecord>(
				mp3Index.values());
	}
}
