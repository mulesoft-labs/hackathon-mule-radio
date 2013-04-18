package org.mule.hackathon.transformers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.hackathon.AgentIndexRecord;
import org.mule.transformer.AbstractMessageTransformer;

import com.mulesoft.mmc.agent.v3.dto.NullPayload;

public class ParticularSongListBuilder extends AbstractMessageTransformer {
	private static final String PROPERTY_KEY = "searchText";

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Object payload = message.getPayload();
		List<AgentIndexRecord> songList = new ArrayList<AgentIndexRecord>();

		if (payload.getClass().isAssignableFrom(ArrayList.class)) {
			List<AgentIndexRecord> list = (List<AgentIndexRecord>) message.getPayload();
			if (list != null && !list.isEmpty()) {
				String searchText = message.getInvocationProperty(PROPERTY_KEY);

				try {
					searchText = URLDecoder.decode(searchText, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					new ArrayList<AgentIndexRecord>();
				}

				for (AgentIndexRecord record : list) {
					if (StringUtils.containsIgnoreCase(record.getCategory(), searchText) || StringUtils.containsIgnoreCase(record.getSong(), searchText) 
							|| StringUtils.containsIgnoreCase(record.getArtist(), searchText)
							|| StringUtils.containsIgnoreCase(record.getAlbum(), searchText)) {

						songList.add(record);

					}
				}
			}

		}

		message.setPayload(songList);

		return message;
	}
}
