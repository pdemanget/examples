package test8;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageProperty implements Property<Image>{
    private ImageView imageView;
    
    public ImageProperty (ImageView imageView) {
      super ();
      this.imageView = imageView;
    }

    @Override
    public Object getBean () {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public String getName () {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void addListener (ChangeListener<? super Image> listener) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void removeListener (ChangeListener<? super Image> listener) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public Image getValue () {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void addListener (InvalidationListener listener) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void removeListener (InvalidationListener listener) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void setValue (Image value) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void bind (ObservableValue<? extends Image> observable) {
      observable.addListener ((obs,old,nevv)->imageView.setImage(nevv));
    }

    @Override
    public void unbind () {
      // TODO Auto-generated method stub
      
    }

    @Override
    public boolean isBound () {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void bindBidirectional (Property<Image> other) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void unbindBidirectional (Property<Image> other) {
      // TODO Auto-generated method stub
      
    }

        
  }
