package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//Test for Controller
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
							"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})	
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
//				ㄴ 가짜mvc. 가짜로 URL과 파라미터 등을 브라우저에서 사용하는 것처럼 만들어서
//					controller를 실행해 볼 수 있다.
	
	@Before//@Before가 적용된 메소드는 모든 테스트 전에 매번 실행되는 메소드가 된다.
	public void setUp() { //setUp()에서는 import할 때 JUnit을 이용한다.
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testList() throws Exception {
	//				ㄴ testList()의 존재를 이용해 GET방식의 호출을 한다.
	//					이후에는 BoardController의 getList()에서 반환된 결과를 이용해
	//					Model에 어떤 데이터가 담겨있는지 확인한다.
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))/*board/list를 출력하는 메소드.*/
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
	
	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				/*									테스트 할 때 MockMvcRequestBuilder의
				 * 									post를 이용하면 POST방식으로 데이터를 
				 * 									전달할 수 있고, param()을 이용해서 전달해야 하는
				 * 									파라미터를 지정할 수 있다.*/
				.param("title", "테스트 새글 제목입니다.")
				.param("content", "테스트 새글 내용입니다.")
				.param("writer", "nxsnji")
				).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	@Test
	public void testGet() throws Exception {
		
		log.info(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/get")
				
				//특정 게시물을 조회할 때 반드시 bno란 파라미터가 필요하므로 param()을 통해 추가실행.
				.param("bno", "93"))
				.andReturn()
				.getModelAndView().getModelMap());
	}
	
	@Test
	public void testModify() throws Exception{
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/modify")
						/*테스트 할 때 MockMvcRequestBuilder의
						 * 	post를 이용하면 POST방식으로 데이터를 
						 * 	전달할 수 있고, param()을 이용해서 전달해야 하는
						 * 	파라미터를 지정할 수 있다.*/
						.param("bno", "92")
						.param("title", "새로운 제목")
						.param("content", "새로운 내용")
						.param("writer", "새로운 작성자"))
				.andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
	@Test
	public void testRemove() throws Exception{
		
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				/*				mockMvc를 이용해서 파라미터를 전달할 때는 문자열로만 처리해야함.*/
				.param("bno", "91")
				).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
}
