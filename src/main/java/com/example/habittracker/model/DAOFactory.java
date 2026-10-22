package com.example.habittracker.model;

/**
 * Creates each DAO once and gives the whole app access to it,
 * so the database tables are only set up a single time.
 */
public class DAOFactory {
    private static final IHabitDAO habitDAO = new SqliteHabitDAO();
    private static final IUserHabitDAO userHabitDAO = new SqliteUserSelectedHabitsDAO();
    private static final IHabitEntryDAO habitEntryDAO = new SqliteHabitEntryDAO();
    private static final ICommunityHabitDAO communityHabitEntryDAO = new SqliteCommunityHabitDAO();

    private DAOFactory() {
    }

    public static IHabitDAO getHabitDAO() {
        return habitDAO;
    }

    public static IUserHabitDAO getUserHabitDAO() {
        return userHabitDAO;
    }

    public static IHabitEntryDAO getHabitEntryDAO() {
        return habitEntryDAO;
    }

    public static ICommunityHabitDAO getCommunityHabitEntryDAO() {
        return communityHabitEntryDAO;
    }


    /**
     * Creates a habit service backed by the app's DAOs.
     */
    public static HabitService createHabitService() {
        return new HabitService(habitEntryDAO, userHabitDAO, habitDAO);
    }
}
