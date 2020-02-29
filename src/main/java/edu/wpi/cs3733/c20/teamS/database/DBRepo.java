package edu.wpi.cs3733.c20.teamS.database;


import java.util.Set;

public interface DBRepo {
    void addNode(NodeData nd);
    void addSetOfNodes(Set<NodeData> set);
    Set<NodeData> getAllNodes();
    Set<NodeData> getAllNodesOfType(String type);
    NodeData getNode(String ID);
    void removeNode(String nodeID);

    Set<EdgeData> getAllEdges();
    void addEdge(EdgeData edge);
    void removeEdge(String edgeID);

    void importStartUpData();
    void purgeTable(String tableName);
    void commit();
    void rollBack();
    void autoCommit(boolean isOn);

    void addEmployee(EmployeeData ed);
    boolean checkLogin(String username, String password);
    EmployeeData getEmployee(String username);
    Set<EmployeeData> getAllEmployeeData();
    void removeEmployee(String username);
    void updateEmployee(EmployeeData emp);

    void addServiceRequestData(ServiceData sd);
    void updateServiceData(ServiceData sd);
    void deleteServiceWithId(int id);

    Set<Integer> getCapableEmployees(String serviceType);
    void addCapability(int ID, String serviceType);
    void removeCapability(int ID, String serviceType);
    boolean checkCapable(int ID, String serviceType);

    Set<String> getEmployeeCapabilities(int employeeID);
    void removeEmployeeCapabilities(int employeeID);

    EmployeeData getEmployeeFromID(int id);
}
