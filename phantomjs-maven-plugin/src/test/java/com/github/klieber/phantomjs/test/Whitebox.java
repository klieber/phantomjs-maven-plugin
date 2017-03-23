package com.github.klieber.phantomjs.test;

import java.lang.reflect.Field;

public class Whitebox {

  public static void setInternalState(Object target, String field, Object value) {
    Class c = target.getClass();

    try {
      Field f = getFieldFromHierarchy(c, field);
      f.setAccessible(true);
      f.set(target, value);
    } catch (Exception var5) {
      throw new RuntimeException("Unable to set internal state on a private field. Please report to mockito mailing list.", var5);
    }
  }


  private static Field getFieldFromHierarchy(Class<?> clazz, String field) {
    Field f;
    for(f = getField(clazz, field); f == null && clazz != Object.class; f = getField(clazz, field)) {
      clazz = clazz.getSuperclass();
    }

    if(f == null) {
      throw new RuntimeException("You want me to set value to this field: '" + field + "' on this class: '" + clazz.getSimpleName() + "' but this field is not declared withing hierarchy of this class!");
    } else {
      return f;
    }
  }


  private static Field getField(Class<?> clazz, String field) {
    try {
      return clazz.getDeclaredField(field);
    } catch (NoSuchFieldException var3) {
      return null;
    }
  }
}
