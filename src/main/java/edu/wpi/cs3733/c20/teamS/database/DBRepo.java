package edu.wpi.cs3733.c20.teamS.database;


import java.util.Set;

public interface DBRepo {
    void addNode(NodeData nd);
    void addSetOfNodes(Set<NodeData> set);
    Set<NodeData> getAllNodes();
    NodeData getNode(String ID);

    Set<EdgeData> getAllEdges();
    void addEdge(EdgeData edge);

    void importStartUpData();
    void purgeTable(String tableName);
    void commit();
    void rollBack();
    void autoCommit(boolean isOn);

    void addEmployee(EmployeeData ed);
    boolean checkLogin(String username, String password);
    EmployeeData getEmployee(String username);
}
