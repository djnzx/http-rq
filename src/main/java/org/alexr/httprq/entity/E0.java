package org.alexr.httprq.entity;

public class E0 {
  private int x;

  public E0(int x) {
    this.x = x;
  }

  public E0() {
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  @Override
  public String toString() {
    return String.format("E0{x=%d}", x);
  }
}
