package com.app.userlist.network.responsemodel;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClsGetListResponse
{

    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private Object message;
    @SerializedName("data")
    private DataBean data;

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public Object getMessage()
    {
        return message;
    }

    public void setMessage(Object message)
    {
        this.message = message;
    }

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public static class DataBean
    {
        @SerializedName("has_more")
        private boolean hasMore;
        @SerializedName("users")
        private List<UsersBean> users;

        public boolean isHasMore()
        {
            return hasMore;
        }

        public void setHasMore(boolean hasMore)
        {
            this.hasMore = hasMore;
        }

        public List<UsersBean> getUsers()
        {
            return users;
        }

        public void setUsers(List<UsersBean> users)
        {
            this.users = users;
        }

        public static class UsersBean
        {
            @SerializedName("name")
            private String name;
            @SerializedName("image")
            private String image;
            @SerializedName("items")
            private List<String> items;

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getImage()
            {
                return image;
            }

            public void setImage(String image)
            {
                this.image = image;
            }

            public List<String> getItems()
            {
                return items;
            }

            public void setItems(List<String> items)
            {
                this.items = items;
            }
        }
    }
}