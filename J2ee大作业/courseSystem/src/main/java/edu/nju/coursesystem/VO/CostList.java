package edu.nju.coursesystem.VO;

import edu.nju.coursesystem.model.UserAccountLog;

import java.util.List;

public class CostList
{
    private List<UserAccountLog> costs;

    public List<UserAccountLog> getCosts() {
        return costs;
    }

    public void setCosts(List<UserAccountLog> costs) {
        this.costs = costs;
    }
}
