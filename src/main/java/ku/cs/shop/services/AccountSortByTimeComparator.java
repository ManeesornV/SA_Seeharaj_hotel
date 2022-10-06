package ku.cs.shop.services;

import ku.cs.shop.models.UserAccount;

import java.util.Comparator;

public class AccountSortByTimeComparator implements Comparator<UserAccount> {

    @Override
    public int compare(UserAccount o1, UserAccount o2) {
        if (o1.getLoginTime().compareTo( o2.getLoginTime()) < 0)  return 1;
        if (o1.getLoginTime().compareTo( o2.getLoginTime()) > 0)  return -1;
        return 0;
    }

}
