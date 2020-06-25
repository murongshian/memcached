package com.example.memcached.controller;

import com.example.memcached.domain.User;
import com.whalin.MemCached.MemCachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemCachedController {

	private MemCachedClient memCachedClient;
	@Autowired
	public MemCachedController(MemCachedClient memCachedClient){
		this.memCachedClient = memCachedClient;
	}

	@GetMapping("key")
	@ResponseBody
	public Object getKey() {
		List<User> list = new ArrayList<>();

		User user = new User();
		user.setId(1);
		user.setName("慕容十安1");
		user.setProvince("浙江1");

		User user1 = new User();
		user1.setId(2);
		user1.setName("慕容十安2");
		user1.setProvince("浙江2");

		list.add(user);
		list.add(user1);

		memCachedClient.set("key", list);
		return memCachedClient.get("key");
	}
}