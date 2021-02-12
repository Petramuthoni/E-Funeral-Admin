package com.freddy.sample.mpesa;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccessorAdapter extends FragmentPagerAdapter {
    public TabsAccessorAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {



      //  switch (i){
           // case 0:
                //ChatsFragment chatsFragment=new ChatsFragment();
                //return  chatsFragment;
          //  case 0:

            GroupchatFragment groupchatFragment=new GroupchatFragment();
            return  groupchatFragment;

            //case 2:
              //  ContactsFragment contactsFragment=new ContactsFragment();
              //  return  contactsFragment;
          //   default:
           //      return null;

        }

    //}

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      //  switch (position){
          //  case 0:
              //  return "Chats";
           // case 1:
                return "Groups";

          //  case 2:
               // return "Contacts";
           // default:
               // return null;

        }

    }
//}
