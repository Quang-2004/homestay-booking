package vn.quangkhongbiet.homestay_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @SpringBootApplication(exclude = {
//         org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//         org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
//         )
public class HomestayBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomestayBookingApplication.class, args);
	}

}
