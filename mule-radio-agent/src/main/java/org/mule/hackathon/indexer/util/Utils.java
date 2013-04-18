package org.mule.hackathon.indexer.util;

import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.mule.hackathon.agent.model.AgentIndexRecord;
import org.mule.hackathon.indexer.Indexer;

import com.google.gson.Gson;

public class Utils {

	private static Gson gson=new Gson();
	
	public static AgentIndexRecord toAgentIndex(ID3v1Tag tag, String id, String fileUrl,String pathLocal){
		AgentIndexRecord ai=new AgentIndexRecord();
		ai.setAlbum(tag.getAlbum().get(0).toString());
		ai.setArtist(tag.getArtist().get(0).toString());
		ai.setCategory(tag.getFirstGenre());
		ai.setFileUrl(fileUrl);
		ai.setPathLocal(pathLocal);
		ai.setId(id);
		ai.setSong(tag.getTitle().get(0).toString());
		return ai;
	}
	public static String getAsJSon(Indexer indexer) {
		return gson.toJson(indexer);
	}

}
