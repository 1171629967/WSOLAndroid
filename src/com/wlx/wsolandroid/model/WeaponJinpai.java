package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;


public class WeaponJinpai extends BmobObject{
	private String pinyin;
    private String name;
    private int    g;
    private int    p;
    private int    f;
    private int    t;
    private int    w;
    private int g_base;
    private int p_base;
    private int f_base;
    private int t_base;
    private int w_base;
    private int move;
    private int jump;
    
    
    
    

    public int getG_base() {
		return g_base;
	}

	public void setG_base(int g_base) {
		this.g_base = g_base;
	}

	public int getP_base() {
		return p_base;
	}

	public void setP_base(int p_base) {
		this.p_base = p_base;
	}

	public int getF_base() {
		return f_base;
	}

	public void setF_base(int f_base) {
		this.f_base = f_base;
	}

	public int getT_base() {
		return t_base;
	}

	public void setT_base(int t_base) {
		this.t_base = t_base;
	}

	public int getW_base() {
		return w_base;
	}

	public void setW_base(int w_base) {
		this.w_base = w_base;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) {
		this.jump = jump;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

}
