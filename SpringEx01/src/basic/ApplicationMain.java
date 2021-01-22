package basic;

public class ApplicationMain {

	public static void main(String[] args) {
		// new를 Spring에게 맡긴다. new 설정을 Spring에 주는 개념
		HelloBean1 helloBean1 = new HelloBean1();
		helloBean1.sayHello("홍길동");
	}

}
