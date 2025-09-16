package com.guatemaltek.postgres.clase3.util;

public final class Texts {

  private Texts() {
  }

  public static String blankToNull(String s) {
    return (s == null || s.isBlank()) ? null : s.trim();
  }
}
