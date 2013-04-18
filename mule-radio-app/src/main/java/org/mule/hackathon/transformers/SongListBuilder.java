package org.mule.hackathon.transformers;

import java.util.ArrayList;
import java.util.List;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.hackathon.AgentIndexRecord;
import org.mule.transformer.AbstractMessageTransformer;

public class SongListBuilder extends AbstractMessageTransformer {
	private static final String PROPERTY_KEY = "songList";

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {

		List<AgentIndexRecord> list = (List<AgentIndexRecord>) message.getPayload();

		List<AgentIndexRecord> songList = message.getInvocationProperty(PROPERTY_KEY);

		if (songList == null) {
			songList = new ArrayList<AgentIndexRecord>();
		}

		songList.addAll(list);
		message.setInvocationProperty(PROPERTY_KEY, songList);

		return message;
	}

}
