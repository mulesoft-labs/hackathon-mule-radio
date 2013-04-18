package org.mule.hackathon.indexer;

import org.mule.hackathon.agent.model.AgentIndexRecord;

import java.io.File;
import java.util.List;

public interface MusicIndexer {

	public void indexFolder(String folder);
	
	public void indexFile(File file);
	
	public AgentIndexRecord find(String fileKey);
	
	public boolean contains(String fileKey);
	
	public void setBaseUri(String uri);
	
	public String getBaseUri(String uri);
	
	public String toJson();
	
	public List<AgentIndexRecord> getAgentIndexRecordList();
}
