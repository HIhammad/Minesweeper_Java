package mine;
/// Authors: Ahmed Eshra, Hussain Hammad
public class Cell {
    private boolean clicked;
    private boolean mine;
    private boolean flag;
    private int mines_around;
    public Cell(){
        this.clicked=false;
        this.mine=false;
        this.flag=false;
        this.mines_around=0;
    }
    public void mines_increment(){
        this.mines_around++;
    }
    public void click(){
        if(this.flag==false) {
            this.clicked = true;
        }
    }
    public void flagIt(){
        if(this.clicked==false){
            this.flag = !this.flag;
        }
    }
    public boolean isClicked() {
        return clicked;
    }
    public boolean isFlaged() {
        return flag;
    }

    public void setMine() {
        this.mine = true;
    }

    public void setMines_around(int mines) {
        this.mines_around = mines;
    }

    public boolean isMine() {
        return mine;
    }

    public int getMines_around() {
        return mines_around;
    }
}
