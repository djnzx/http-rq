package org.alexr.httprq;

import java.io.IOException;

public class App {
  public static void main(String[] args) throws IOException {
    Methods m = new Methods();
    //m.method1();
    //m.method2();
    //m.method3();
    //m.method4();
    //m.method5();
    m.method6_post_w_gson_from_plain();
    m.method7_post_w_jackson_from_plain();
    m.method8_post_w_jackson_smart();
    m.method9_post_w_jackson_super_smart();
  }
}
