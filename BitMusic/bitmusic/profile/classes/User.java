/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bitmusic.profile.classes;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import sun.awt.shell.ShellFolder;
import bitmusic.music.data.Song;
import bitmusic.music.data.SongLibrary;
import bitmusic.profile.utilities.ProfileExceptionType;
import bitmusic.profile.utilities.ProfileExceptions;

/**
 *
 * @author reaneyol, MilioPeralta, Jérémy, Fabien
 */
public class User implements Serializable {

    //########################## ATTRIBUTES ##########################//
    private static final long serialVersionUID = 402L;
    /**
     *
     */
    private String userId;

    /**
     *
     */
    private String login;

    /**
     *
     */
    private transient String password;

    /**
     *
     */
    private Calendar birthDate;

    /**
     *
     */
    private String firstName;

    /**
     *
     */
    private String lastName;

    /**
     *
     */
    private String avatarPath;

    /**
     *
     */
    private SongLibrary localSongs;

    /**
     *
     */
    private static final File myDocumentsFolder = (File) ShellFolder.get("fileChooserDefaultFolder");

    /**
     *
     */
    private ArrayList<Category> categories;

    //######################### CONSTRUCTORS ###########################//

    /**
     *
     * @param login
     * @param password
     * @param firstName
     * @param lastName
     * @param birthDate
     * @param avatarPath
     */
    public User(String login, String password, String firstName, String lastName, Calendar birthDate, String avatarPath) {
        this.userId = UUID.randomUUID().toString();
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.avatarPath = avatarPath;
        this.categories = new ArrayList<Category>();
        categories.add(new Category("Général"));
    }

    /**
     *
     * @param login
     * @param password
     */
    public User(String login, String password) {
        this(login, password, null, null, null, null);
    }

    //########################### METHODS ##############################//

    /**
     *
     * @return
     */
    public SongLibrary getLocalSongs() {
        return localSongs;
    }

    /**
     *
     * @param localSongs
     */
    public void setLocalSongs(SongLibrary localSongs) {
        this.localSongs = localSongs;
    }

    /**
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(String userId) throws ProfileExceptions {
        if (userId == null || userId.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.UserIdEmptyName);
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) throws ProfileExceptions{
        if (login == null || login.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.LoginEmptyName);
        this.login = login; 
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) throws ProfileExceptions{
        if (password == null || password.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.PasswordEmptyName);
        else this.password = password;
    }

    /**
     *
     * @return
     */
    public Calendar getBirthDate() {
        return birthDate;
    }

    /**
     *
     * @param birthDate
     */
    public void setBirthDate(Calendar birthDate) throws ProfileExceptions{
        if (birthDate == null || birthDate.toString().isEmpty()) throw new ProfileExceptions(ProfileExceptionType.BirthdateEmptyName);
        else this.birthDate = birthDate;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) throws ProfileExceptions{
        if (firstName == null || firstName.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.FirstNameEmpty);
        else this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) throws ProfileExceptions{
        if (lastName == null || lastName.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.LastNameEmpty);
        else this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     *
     * @param avatarPath
     */
    public void setAvatarPath(String avatarPath) throws ProfileExceptions{
        if (avatarPath == null || avatarPath.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.AvatarPathEmpty);
        else this.avatarPath = avatarPath;
    }

    /**
     *
     * @return
     */
    public ArrayList<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @return
     */
    public Category getCategoryById(String categoryId) throws ProfileExceptions {
        for(Category cat : this.categories) {
            if(cat.getId() == categoryId) {
                return cat;
            }
        }
        throw new ProfileExceptions(ProfileExceptionType.CategoryNotFound);
    }

    /**
     *
     * @param categories
     */
    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    /**
     *
     * @param userId
     * @return
     */
    public User getContact(String userId) {
    	User usr = null;
    	for (Category cat : categories) {
    		usr = cat.findContact(userId);
    		if (usr != null) break;
    	}
        return usr;
    }

    /**
     *
     * @param name
     */
    public void addCategory(String name) throws ProfileExceptions {
        if (name == null || name.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.CategoryEmptyName);
        else categories.add(new Category(name));
    }

    /**
     *
     * @param id
     * @param newName
     */
    public void updateCategory(String categoryId, String newName) throws ProfileExceptions {
       if (newName == null || newName.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.CategoryEmptyName);
       else this.getCategoryById(categoryId).setName(newName);
    }

    /**
     *
     * @param id
     */
    public void deleteCategory(String categoryId) throws ProfileExceptions {
        categories.remove(this.getCategoryById(categoryId));
    }

    /**
     *
     * @param user
     * @param idCategory
     */
    public void addContact(User user, String categoryId) throws ProfileExceptions {
        this.getCategoryById(categoryId).addUser(user);
    }

    /**
     *
     * @param user
     * @param idCategory
     */
    public void removeContact(User user, String categoryId) throws ProfileExceptions {
         this.getCategoryById(categoryId).deleteUser(user);
    }

    /**
     *
     * @return
     */
    public SongLibrary getSongs() {
        return localSongs;
    }

    /**
     *
     * @param song
     */
    public void addSong(Song song) {
        this.localSongs.addSong(song);
    }

    /**
     *
     * @param song
     */
    public void deleteSong(String songId) throws ProfileExceptions{
        if (songId == null || songId.isEmpty()) throw new ProfileExceptions(ProfileExceptionType.SongEmptyName);
        else this.localSongs.removeSong(songId);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Login : " + this.login + "\nUserID : " + this.userId + "\n";
    }

    /**
     * Format the birthdate to be used in the folder name
     * 
     * @return
     */
	public String getTransformedBirthday() {
        int year = this.birthDate.get(Calendar.YEAR);
        int month = this.birthDate.get(Calendar.MONTH);
        int day = this.birthDate.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(year) + Integer.toString(month) + Integer.toString(day);
    }

    /**
     * Return the encrypted password using SHA-1
     *
     * @return
     */
    public String getEncryptedPassword() {
    	ConfigurablePasswordEncryptor pwdEncryptor = new ConfigurablePasswordEncryptor();
		pwdEncryptor.setAlgorithm("SHA-1");
		return pwdEncryptor.encryptPassword(password);
    }

	public String getFolderName() {
		return login + "_" + getTransformedBirthday();
    }
}
