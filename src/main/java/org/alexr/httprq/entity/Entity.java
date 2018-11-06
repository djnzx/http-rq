package org.alexr.httprq.entity;

import java.util.List;

public class Entity {
  private int id;
  private String name;
  private List<E0> list;
  private E1 e1;

  public Entity() {
  }

  public Entity(int id, String name, List<E0> items) {
    this(id, name, items, new E1(99));
  }

  public Entity(int id, String name, List<E0> items, E1 e1) {
    this.id = id;
    this.name = name;
    this.list = items;
    this.e1 = e1;
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

  public E1 getE1() {
    return e1;
  }

  public void setE1(E1 e1) {
    this.e1 = e1;
  }

  @Override
  public String toString() {
    return String.format("Entity{id=%d, name='%s', list=%s, e1=%s}", id, name, list, e1);
  }
}
