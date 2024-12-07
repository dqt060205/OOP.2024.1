package com.group19.gameobject;

class GameLevel {
    protected int levelNumber;
    protected int minimumScore;
    protected int maximumScore;
    // protected int currentScore;
    protected int speed;
    protected boolean isUnlocked;
        private int currentScore;
    
        public GameLevel(int levelNumber, int minimumScore, int maximumScore, int speed, boolean isUnlocked) {
            this.levelNumber = levelNumber;
            this.minimumScore = minimumScore;
            this.maximumScore = maximumScore;
            this.currentScore = 0;
        this.speed = speed;
        this.isUnlocked = isUnlocked;
    }
    // Kiểm tra đủ điều kiện qua màn chưa
    public boolean hasWon(Dinosaur dinosaur) {
        return dinosaur.getLives() >= minimumScore;
    }
    // // Chưa có thuộc tính score trong Dinosaur. 
    // public int getScore() {
    //     return Dinosaur.score;
    // }
    // Chưa có thuộc tính lives trong Dinosaur
    
    public int getLives(Dinosaur dinosaur) {
        return dinosaur.getLives();
    }
    public int getMaximumScore() {
        return maximumScore;
    }
    //hàm kiêmr tra xem màn đã được mở khóa chưa
    public boolean isUnlocked() {
        return this.isUnlocked;
    }
    //hàm kiểm tra xem màn này đã kết thúc chưa
    public boolean isEnded(Dinosaur dinosaur) {
        return this.hasWon(dinosaur) || dinosaur.getLives()== 0;
    }

    public void unlock() {
        this.isUnlocked = true;
    }

}

/*package com.group19.gameobject;

class GameLevel {
    protected int levelNumber;
    protected int minimumScore;
    protected int maximumScore;
    // protected int currentScore;
    protected int speed;
    protected boolean isUnlocked;

    public GameLevel(int levelNumber, int minimumScore, int maximumScore, int speed, boolean isUnlocked) {
        this.levelNumber = levelNumber;
        this.minimumScore = minimumScore;
        this.maximumScore = maximumScore;
        this.currentScore = 0;
        this.speed = speed;
        this.isUnlocked = isUnlocked;
    }
    // Kiểm tra đủ điều kiện qua màn chưa
    public boolean hasWon(Dinosaur dinosaur) {
        return dinosaur.getLives() >= minimumScore;
    }
    // Chưa có thuộc tính score trong Dinosaur. 
    public int getScore() {
        return Dinosaur.score;
    }
    // Chưa có thuộc tính lives trong Dinosaur
    public int getLives(Dinosaur dinosaur) {
        return dinosaur.getLives();
    }
    public int getMaximumScore() {
        return maximumScore;
    }
    //hàm kiêmr tra xem màn đã được mở khóa chưa
    public boolean isUnlocked() {
        return this.isUnlocked;
    }
    //hàm kiểm tra xem màn này đã kết thúc chưa
    public boolean isEnded(Dinosaur dinosaur) {
        return this.hasWon() || dinosaur.getLives()== 0;
    }

    public void unlock() {
        this.isUnlocked = true;
    }

}
*/
