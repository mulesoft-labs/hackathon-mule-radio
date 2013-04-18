package org.mule.hackathon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.store.ListableObjectStore;
import org.mule.api.store.ObjectStoreException;

/**
*   
*   @author Mulesoft Inc.
*
*/
public class MuleRadioObjectStore implements ListableObjectStore<Serializable> {

    private Map<Serializable, Serializable> values = new LinkedHashMap<Serializable, Serializable>();

    @Override
    public boolean contains(Serializable key) throws ObjectStoreException {
        return this.values.containsKey(key);
    }

    @Override
    public void store(Serializable key, Serializable value)
            throws ObjectStoreException {
        this.values.put(key, value);

    }

    @Override
    public Serializable retrieve(Serializable key) throws ObjectStoreException {
        return this.values.get(key);
    }

    @Override
    public Serializable remove(Serializable key) throws ObjectStoreException {
        Serializable value = this.values.get(key);
        if (value != null) {
            this.values.remove(key);
        }
        return value;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    @Override
    public void open() throws ObjectStoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() throws ObjectStoreException {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Serializable> allKeys() throws ObjectStoreException {
        return new ArrayList<Serializable>(values.keySet());
    }
}