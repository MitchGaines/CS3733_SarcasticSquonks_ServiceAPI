package edu.wpi.cs3733d18.SquonksAPI.data;

import edu.wpi.cs3733d18.SquonksAPI.database.Storage;

import java.util.Objects;

/**
 * A Node is an entity to hold Node information.
 * Nodes are the the pixel coordinates, and name of certain
 * real world locations in the hospital.
 */
public class Node {

    /**
     * Stores the node id.
     */
    private String node_id; // unique id of node

    /**
     * Stores the x coordinate of the node.
     */
    private int x_coord;

    /**
     * Stores the y coordinate of the node.
     */
    private int y_coord;

    /**
     * Stores the floor the node is on
     */
    private String floor;

    /**
     * Stores the buidling the node is located.
     */
    private String building;

    /**
     * Stores the type of node.
     */
    private String node_type;

    /**
     * Stores the full name of the node.
     */
    private String long_name;

    /**
     * Stores the abbreviated name of the node.
     */
    private String short_name;

    /**
     * Stores the team assigned to this node.
     */
    private String team_assigned;

    /**
     * Stores the three dimensional x coordinate of the node.
     */
    private int x_coord_3d;

    /**
     * Stores the three dimensional y coordinate of the node.
     */
    private int y_coord_3d; // 3d y coordinate of node

    /**
     * Tells whether an node is disabled or not.
     */
    private boolean disabled;

    /**
     * Constructs a node using its coordinate positions, its id, the floor
     * the building its in, the long name, the abbreviated name, and the team
     * assigned to the node area.
     * @param id    the node id.
     * @param x     the 2d x coordinate.
     * @param y     the 2d y coordinate.
     * @param floor the floor the node is on.
     * @param bldg  the building the node is in.
     * @param type  the type of node.
     * @param lName the full name of the node.
     * @param sName the abbreviated name of the node.
     * @param team  the team assigned to the node.
     * @param x3d   the 3d x coordinate.
     * @param y3d   the 3d y coordinate.
     * @param disabled a boolean for whether or not the node is disabled.
     */
    public Node(String id, int x, int y, String floor, String bldg, String type,
                String lName, String sName, String team, int x3d, int y3d, boolean disabled) {
        this.node_id = id;
        this.x_coord = x;
        this.y_coord = y;
        this.floor = floor;
        this.building = bldg;
        this.node_type = type;
        this.long_name = lName;
        this.short_name = sName;
        this.team_assigned = team;
        this.x_coord_3d = x3d;
        this.y_coord_3d = y3d;
        this.disabled = disabled;
    }

    /**
     * Generates nodes for pathfinding
     */
    public static void generateAllNodes() {
        Storage storage = Storage.getInstance();

        storage.saveNode(new Node("ACONF00103",1648,2968,"3","BTM","CONF","BTM Conference Center","BTM Conference","Team A",1029,2246,false));
        storage.saveNode(new Node("ADEPT00101",1401,2628,"1","BTM","DEPT","Neuroscience Waiting Room","Neuro Waiting Room","Team A",938,2132,false));
        storage.saveNode(new Node("ADEPT00201",1720,2847,"1","BTM","DEPT","MS Waiting","MS Waiting","Team A",1080,2261,false));
        storage.saveNode(new Node("ALABS00103",1611,2678,"3","BTM","LABS","Innovation Hub","Innovation Hub","Team A",1079,2121,false));
        storage.saveNode(new Node("ALABS001L2",1667,2682,"L2","BTM","LABS","MRI/CT Scan Imaging","MRI/CT Imaging","Team A",1029,2217,false));
        storage.saveNode(new Node("BSERV00102",2853,1067,"2","45 Francis","SERV","Patient Financial Services","SERV B0102","Team B",2522,1643,false));
        storage.saveNode(new Node("CCONF002L1",2665,1043,"L1","45 Francis","CONF","Medical Records Conference Room Floor L1","Conf C002L1","Team C",2393,1628,false));
        storage.saveNode(new Node("CDEPT002L1",1980,845,"L1","Tower","DEPT","Day Surgery Family Waiting Floor L1","Department C002L1","Team C",1970,1340,false));
        storage.saveNode(new Node("CSERV001L2",2015,1280,"L2","45 Francis","SERV","Interpreter Services Floor L2","Service C001L2","Team C",1864,1609,false));
        storage.saveNode(new Node("DINFO00202",3490,825,"2","15 Francis","INFO","Security Desk Thorn Floor 2","Security Desk","Team D",3050,1675,false));
        storage.saveNode(new Node("DEXIT00102",4295,750,"2","15 Francis","EXIT","15 Lobby Entrance Floor 2","15 Lobby Entrance","Team D",3680,1845,false));
        storage.saveNode(new Node("EINFO00101",2460,850,"1","45 Francis","INFO","Connors Center Security Desk Floor 1","Security 1","Team E",2305,1440,false));
        storage.saveNode(new Node("EDEPT00401",2790,1380,"1","45 Francis","DEPT","Rehabilitation Services Floor 1","Rehab 1","Team E",2350,1810,false));
        storage.saveNode(new Node("FDEPT00101",1617,825,"1","Tower","DEPT","Center for International Medicine","CIM","Team F",1699,1193,false));
        storage.saveNode(new Node("FDEPT00201",1610,1120,"1","Tower","DEPT","Bretholtz Center for Patients and Families","Bretholtz WR","Team F",1587,1343,false));
        storage.saveNode(new Node("GCONF02001",1493,2117,"1","Shapiro","CONF","Shapiro Board Room MapNode 20 Floor 1","Shapiro Board Room","Team G",1140,1859,false));
        storage.saveNode(new Node("GDEPT01403",1580,1904,"3","Shapiro","DEPT","Watkins Clinic C Node 14 Floor 3","Watkins Clinic C","Team G",1276,1728,false));
        storage.saveNode(new Node("GLABS003L2",1729,2089,"L2","Shapiro","LABS","Cardiovascular Imaging Center Floor L2","Cardiovascular Imaging Center","Team G",1336,1972,false));
        storage.saveNode(new Node("HRETL00102",1935,860,"2","Tower","RETL","Garden Cafe","Garden Cafe","Team H",1901,1262,false));
    }

    /**
     * Retrieves the node id.
     *
     * @return the node id.
     */
    public String getNodeID() {
        return this.node_id;
    }

    /**
     * Sets the node id.
     *
     * @param node_id the node id.
     */
    public void setNodeID(String node_id) {
        this.node_id = node_id;
    }

    /**
     * Retrieves the two dimensional x coordinate.
     *
     * @return the 2d x coordinate.
     */
    public int getXCoord() {
        return this.x_coord;
    }

    /**
     * Sets the 2 dimensional x coordinate.
     *
     * @param x_coord the 2d x coordinate.
     */
    public void setXCoord(int x_coord) {
        this.x_coord = x_coord;
    }

    /**
     * Retrieves the two dimensional y coordinate.
     *
     * @return the 2d y coordinate.
     */
    public int getYCoord() {
        return this.y_coord;
    }

    /**
     * Sets the two dimensional y coordinate.
     *
     * @param y_coord the 2d y coordinate.
     */
    public void setYCoord(int y_coord) {
        this.y_coord = y_coord;
    }

    /**
     * Retrieves the floor the node is on.
     *
     * @return the floor.
     */
    public String getNodeFloor() {
        return this.floor;
    }

    /**
     * Sets the floor node.
     *
     * @param floor the floor the node is on.
     */
    public void setNodeFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Retrieves the building the node is in.
     *
     * @return the building.
     */
    public String getNodeBuilding() {
        return this.building;
    }

    /**
     * Sets the building that the node is in.
     *
     * @param building the building the node is in.
     */
    public void setNodeBuilding(String building) {
        this.building = building;
    }

    /**
     * Retrieves the type of node.
     *
     * @return the node type.
     */
    public String getNodeType() {
        return this.node_type;
    }

    /**
     * Sets the node type.
     *
     * @param node_type the node's type.
     */
    public void setNodeType(String node_type) {
        this.node_type = node_type;
    }

    /**
     * Retrieves the long version of the node name.
     *
     * @return the long name of the node.
     */
    public String getLongName() {
        return this.long_name;
    }

    /**
     * Sets the long version of the node name.
     *
     * @param long_name the long name of the node.
     */
    public void setLongName(String long_name) {
        this.long_name = long_name;
    }

    /**
     * Retrieves the abbreviated version of the node's name.
     *
     * @return the short name.
     */
    public String getShortName() {
        return this.short_name;
    }

    /**
     * Sets the abbreviated version of the node's name
     *
     * @param short_name the abreviated version of the node name.
     */
    public void setShortName(String short_name) {
        this.node_type = short_name;
    }

    /**
     * Retrieves the team assigned to the node.
     *
     * @return the team assigned.
     */
    public String getTeamAssigned() {
        return this.team_assigned;
    }

    /**
     * Sets the team assigned to a node.
     *
     * @param team_assigned the team assigned.
     */
    public void setTeamAssigned(String team_assigned) {
        this.team_assigned = team_assigned;
    }

    /**
     * Retrieves the 3d x coordinate.
     *
     * @return the 3d x coordinate.
     */
    public int getXCoord3D() {
        return this.x_coord_3d;
    }

    /**
     * Sets the 3d x coordinate.
     *
     * @param x_coord_3d the 3d x coordinate.
     */
    public void setXCoord3D(int x_coord_3d) {
        this.x_coord_3d = x_coord_3d;
    }

    /**
     * Retrieves the 3d y coordinate.
     *
     * @return the 3d y coordinate.
     */
    public int getYCoord3D() {
        return this.y_coord_3d;
    }

    /**
     * Sets the 3d y coordinate.
     *
     * @param y_coord_3d the 3d y coordinate.
     */
    public void setYCoord3D(int y_coord_3d) {
        this.y_coord_3d = y_coord_3d;
    }

    public boolean isDisabled(){
        return disabled;
    }

    public void setDisabled(boolean disabled){
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return long_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x_coord == node.x_coord &&
                y_coord == node.y_coord &&
                x_coord_3d == node.x_coord_3d &&
                y_coord_3d == node.y_coord_3d &&
                Objects.equals(node_id, node.node_id) &&
                Objects.equals(floor, node.floor) &&
                Objects.equals(building, node.building) &&
                Objects.equals(node_type, node.node_type) &&
                Objects.equals(long_name, node.long_name) &&
                Objects.equals(short_name, node.short_name) &&
                Objects.equals(team_assigned, node.team_assigned);
    }

    @Override
    public int hashCode() {

        return Objects.hash(node_id, x_coord, y_coord, floor, building, node_type, long_name, short_name, team_assigned, x_coord_3d, y_coord_3d);
    }
}


