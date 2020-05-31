package com.wombat.restaurantapp.report;

import com.wombat.restaurantapp.menu.MenuItem;

import java.util.Objects;

public class TopItem {
    private MenuItem menuItem;
    private Integer count;

    public TopItem(MenuItem menuItem, Integer count) {
        this.menuItem = menuItem;
        this.count = count;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopItem topItem = (TopItem) o;
        return menuItem.equals(topItem.menuItem) &&
                count.equals(topItem.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItem, count);
    }
}
