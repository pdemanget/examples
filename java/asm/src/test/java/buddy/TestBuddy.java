package buddy;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class TestBuddy {
  
  @Test
  public void inject() throws InstantiationException, IllegalAccessException{
    Class<?> dynamicType = new ByteBuddy()
  .subclass(Object.class)
  .method(ElementMatchers.named("toString"))
  .intercept(FixedValue.value("Hello World!"))
  .make()
  .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
  .getLoaded();
 
   Assert.assertEquals(dynamicType.newInstance().toString(), "Hello World!");
  }
  
  @Test
  public void inject2() throws InstantiationException, IllegalAccessException{
    Class<?> dynamicType = new ByteBuddy()
  .subclass(ArrayList.class)
  .method(ElementMatchers.named("toString"))
  .intercept(FixedValue.value("Hello World!"))
  .make()
  .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
  .getLoaded();
 
   Assert.assertEquals(dynamicType.newInstance().toString(), "Hello World!");
  }

}
