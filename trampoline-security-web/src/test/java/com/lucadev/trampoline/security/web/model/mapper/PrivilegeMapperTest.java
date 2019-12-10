package com.lucadev.trampoline.security.web.model.mapper;

import com.lucadev.trampoline.security.persistence.entity.Privilege;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link PrivilegeMapper}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 6/8/19
 */
@SpringBootTest(classes = PrivilegeMapperImpl.class)
@RunWith(SpringRunner.class)
public class PrivilegeMapperTest {

	@Autowired
	private PrivilegeMapper privilegeMapper;

	@Test
	public void mapperNotNull() {
		assertNotNull(privilegeMapper);
	}

	@Test
	public void toName() {
		String expected = "SOME_PRIVILEGE";
		Privilege privilege = new Privilege();
		privilege.setName(expected);

		String result = privilegeMapper.toName(privilege);
		assertEquals(expected, result);

	}

	@Test
	public void toNames() {
		Set<Privilege> privileges = new HashSet<>();
		Set<String> expected = new HashSet<>();
		for (int x = 0; x < 10; x++) {
			Privilege priv = new Privilege();
			String name = "SOME_PRIVILEGE_" + x;
			priv.setName(name);
			privileges.add(priv);
			expected.add(name);
		}
		Set<String> result = privilegeMapper.toNames(privileges);
		assertEquals(expected, result);
	}

}
