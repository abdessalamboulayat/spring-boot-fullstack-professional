package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	Calculator calculator = new Calculator();

	@Test
	void itShouldAddTwoNumbers() {
		//given
		int nbr1 = 20;
		int nbr2 = 2;
		//when
		int result = calculator.add(nbr1,nbr2);
		//then
		assertThat(result).isEqualTo(21);
	}

	class Calculator{
		int add(int x1, int x2){
			return x1+x2;
		}
	}

}
