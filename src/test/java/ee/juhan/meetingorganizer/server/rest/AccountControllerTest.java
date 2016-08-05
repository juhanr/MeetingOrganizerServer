package ee.juhan.meetingorganizer.server.rest;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import javax.servlet.http.Cookie;

import ee.juhan.meetingorganizer.server.Application;
import ee.juhan.meetingorganizer.server.TestUtil;
import ee.juhan.meetingorganizer.server.core.domain.Account;
import ee.juhan.meetingorganizer.server.core.repository.AccountRepository;
import ee.juhan.meetingorganizer.server.rest.domain.ContactDto;
import ee.juhan.meetingorganizer.server.service.AccountService;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class AccountControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private AccountRepository accountRepository;

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountService accountService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(accountController)
				.setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void checkContactsSuccess() throws Exception {
		Account testAccount = TestUtil.generateTestAccount(accountRepository);
		assertNotNull(testAccount);
		List<ContactDto> testContacts = TestUtil.generateTestContactsList(1);
		ContactDto testContact = testContacts.get(0);
		Cookie goodCookie = new Cookie("sid", testAccount.getSid());

		when(accountService.checkContacts(eq(testAccount.getId()), any(testContacts.getClass())))
				.thenReturn(testContacts);

		String requestPath = ControllerConstants.ACCOUNT_PATH + "/" + testAccount.getId() +
				ControllerConstants.CHECK_CONTACTS_PATH;
		String json = new Gson().toJson(testContacts);
		mockMvc.perform(post(requestPath).content(json).cookie(goodCookie)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].accountId", is(testContact.getAccountId())))
				.andExpect(jsonPath("$.[0].name", is(testContact.getName())))
				.andExpect(jsonPath("$.[0].email", is(testContact.getEmail())))
				.andExpect(jsonPath("$.[0].phoneNumber", is(testContact.getPhoneNumber())));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void checkContactsFail() throws Exception {
		Account testAccount = TestUtil.generateTestAccount(accountRepository);
		assertNotNull(testAccount);
		List<ContactDto> testContacts = TestUtil.generateTestContactsList(1);
		Cookie badCookie = new Cookie("sid", testAccount.getSid() + "j");
		when(accountService.checkContacts(eq(testAccount.getId()), any(testContacts.getClass())))
				.thenReturn(null);

		String requestPath = ControllerConstants.ACCOUNT_PATH + "/" + testAccount.getId() +
				ControllerConstants.CHECK_CONTACTS_PATH;
		String json = new Gson().toJson(testContacts);
		mockMvc.perform(post(requestPath).content(json).cookie(badCookie)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

}
