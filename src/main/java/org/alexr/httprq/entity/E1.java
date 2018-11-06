package org.alexr.httprq.entity;

public class E1 {
  private int val;

  public E1(int val) {
    this.val = val;
  }

  public E1() {
  }

  public int getVal() {
    return val;
  }

  public void setVal(int val) {
    this.val = val;
  }

  @Override
  public String toString() {
    return String.format("E1{val=%d}", val);
  }
}
