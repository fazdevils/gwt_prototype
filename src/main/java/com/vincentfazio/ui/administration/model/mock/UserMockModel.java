package com.vincentfazio.ui.administration.model.mock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vincentfazio.ui.bean.UserDetailsBean;
import com.vincentfazio.ui.model.UserDetailsModel;
import com.vincentfazio.ui.model.UserListModel;

public class UserMockModel implements UserDetailsModel, UserListModel {
    
    private Map<String, UserDetailsBean> mockUserMap = new TreeMap<String, UserDetailsBean>(new UserNameComparator());
    //private int callCount = 0;

    public UserMockModel() {
        super();
        for (int i=0; i < 4000; ++i) {
            String userId = "User " + (i+1);
            UserDetailsBean user = new UserDetailsBean();
            user.setEmail("mock.user" + (i+1) + "@gwtmock.com");
            user.setUserId(userId);
            user.setName("Test User");
            user.setAdministrator(false);
            user.setCustomer(false);
            user.setVendor(false);
            mockUserMap.put(userId, user);
        }
        
        String userId = "A Long User Name - Longer than Most Will Ever Be";
        UserDetailsBean user = new UserDetailsBean();
        user.setEmail("mock.user@gwtmock.com");
        user.setUserId(userId);
        user.setName("Test User");
        user.setAdministrator(false);
        user.setCustomer(false);
        user.setVendor(false);
        mockUserMap.put(userId, user);

    }

    @Override
    public void getUser(String userId, AsyncCallback<UserDetailsBean> asyncCallback) {
        UserDetailsBean user = getUser(userId);
        if (null == user) {
            asyncCallback.onFailure(new Exception("User not found"));
        } else {
            asyncCallback.onSuccess(user);
        }
    }

    @Override
    public void saveUser(UserDetailsBean user, AsyncCallback<String> asyncCallback) 
    {
        mockUserMap.put(user.getUserId(), copyUser(user));        
        asyncCallback.onSuccess("Saved");  
    }

    @Override
    public void createUser(String userId, AsyncCallback<String> asyncCallback) {
        UserDetailsBean user = new UserDetailsBean();
        user.setUserId(userId);
        mockUserMap.put(user.getUserId(), copyUser(user));        
        asyncCallback.onSuccess("Created");  
    }

    @Override
    public void deleteUser(String userId, AsyncCallback<String> asyncCallback) {
        mockUserMap.remove(userId);        
        asyncCallback.onSuccess("Deleted");
    }
    
    @Override
    public void getUserList(AsyncCallback<ArrayList<String>> asyncCallback) {
	    asyncCallback.onSuccess(new ArrayList<String>(mockUserMap.keySet()));
    }
    
   	@Override
    public void getUserList(String role, AsyncCallback<ArrayList<String>> asyncCallback) {
        ArrayList<String> users = new ArrayList<String>();
        for (UserDetailsBean userDetails: mockUserMap.values()) {
            if (role.equalsIgnoreCase("customer")) {
                if (userDetails.isCustomer()) {
                    users.add(userDetails.getUserId());
                }
            } else if (role.equalsIgnoreCase("vendor")) {
                if (userDetails.isVendor()) {
                    users.add(userDetails.getUserId());
                }
            } else if (role.equalsIgnoreCase("admin")) {
                if (userDetails.isAdministrator()) {
                    users.add(userDetails.getUserId());
                }
            }
        }
            asyncCallback.onSuccess(users);
    }

    private UserDetailsBean getUser(String userId) {
        UserDetailsBean user = mockUserMap.get(userId);
        if (null == user) {
            return null;
        }
        return copyUser(user);
    }
    
    private UserDetailsBean copyUser(UserDetailsBean bean) {
        UserDetailsBean copy = new UserDetailsBean();
        copy.setEmail(bean.getEmail());
        copy.setName(bean.getName());
        copy.setPhone(bean.getPhone());
        copy.setTitle(bean.getTitle());
        copy.setUserId(bean.getUserId());
        copy.setAdministrator(bean.isAdministrator());
        copy.setCustomer(bean.isCustomer());
        copy.setVendor(bean.isVendor());
        return copy;
    }

    static class UserNameComparator implements Comparator<String> {

        @Override
        public int compare(String arg1, String arg2) {
            Comparable<?> user1;
            Comparable<?> user2;
            if (arg1.startsWith("User ")) {
                try {
                    user1 = new Integer(arg1.replace("User ", ""));
                    if (arg2.startsWith("User ")) {
                        user2 = new Integer(arg2.replace("User ", ""));
                    } else {
                        user1 = arg1;
                        user2 = arg2;
                    }
                } catch (NumberFormatException e) {
                    user1 = arg1;
                    user2 = arg2;                    
                }
            } else {
                user1 = arg1;
                user2 = arg2;
            }
            
            
            if (isaInteger(user1) && isaInteger(user2)) {
                Integer userId1 = (Integer)user1;
                return userId1.compareTo((Integer)user2);
            }
            
            int compare = user1.toString().toLowerCase().compareTo(user2.toString().toLowerCase());
            if (0 == compare) {
                compare = user1.toString().compareTo(user2.toString());                
            }
            return compare;
        }
        
        private boolean isaInteger(Object o1) {
            return o1 instanceof Integer;
        }

    }

    public int getNumberOfUsers() {
        return mockUserMap.size();
    }

    public boolean contains(String userId) {
        return mockUserMap.containsKey(userId);
    }
}
