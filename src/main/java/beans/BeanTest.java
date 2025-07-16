package beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("beanTest")
@RequestScoped
public class BeanTest {

    public void click() {
        System.out.println("aqui");
    }
}
