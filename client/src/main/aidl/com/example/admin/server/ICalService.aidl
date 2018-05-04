// ICalService.aidl
package com.example.admin.server;

import java.util.List;
import com.example.admin.client.MyObject;

interface ICalService {
	String getMessage(String name);
	int getResult(int val1, int val2);
	List<MyObject> getAllObjects();
}
