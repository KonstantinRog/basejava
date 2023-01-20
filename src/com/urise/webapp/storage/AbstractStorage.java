package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void update(Resume resume) {
        Object key = getExistedSearchKey(resume.getUuid());
        doUpdate(resume, key);
    }

    public void save(Resume r) {
        Object key = getNotExistedSearchKey(r.getUuid());
        doSave(r, key);
    }

    public Resume get(String uuid) {
        Object key = getExistedSearchKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) {
        Object key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    protected abstract void doUpdate(Resume resume, Object key);

    protected abstract void doSave(Resume r, Object key);

    protected abstract boolean isExist(Object key);

    protected abstract Object getKey(String uuid);

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}
