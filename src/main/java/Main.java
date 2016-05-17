import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeincrementer.dao.JEStatsDao;

public class Main {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		JEStatsDao dao = (JEStatsDao) ctx.getBean(JEStatsDao.class);
		
		System.out.println("DAO -> " + dao);
	}

}
