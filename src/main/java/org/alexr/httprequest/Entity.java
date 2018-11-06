package org.alexr.httprequest;

import java.util.List;

public class Entity {
    private int id;
    private String name;
    private List<E0> list;

    public Entity() {
    }

    public Entity(int id, String name, List<E0> items) {
        this.id = id;
        this.name = name;
        this.list = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<E0> getList() {
        return list;
    }

    public void setList(List<E0> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return String.format("Entity:{id=%d, name='%s', list=%s}", id, name, list);
    }
}
