package aoop.asteroids.model;

import java.io.Serializable;

import javax.persistence.*;
 
@Entity
public class Participant implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
 
    @Id @GeneratedValue
    private long id;
 
    private int highScore = 0;
    

	private String nickname;
 
    public Participant() {
    }
 
    public Participant(int highScore, String nickname) {
    	this.highScore = highScore;
    	this.nickname = nickname;
    }
 
    public Long getId() {
        return id;
    }

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String toString(){
		return this.nickname +": " + this.highScore;
	}
	
  @Override
    public int compareTo(Object o) {
	  Participant other = (Participant) o;
        return other.highScore - this.highScore;
    }
	  
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + highScore;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (highScore != other.highScore)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}
    
    
}
