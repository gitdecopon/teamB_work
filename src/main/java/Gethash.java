import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//https://www.early2home.com/blog/programming/java/post-4090.html#google_vignette
//参考サイト

//String型引数をハッシュ値(sha3_512)に変換して戻り値で返す。
public class Gethash {
	private String code;
	private String strHashCode;
	
	public Gethash(String str) {
		//引数をprivate変数codeへ代入
		this.code = str;
	}

	public String hash() {
	    // SHA3-512のハッシュ値を返すメソッド
	    MessageDigest sha3_512 = null;
	    try {
	      sha3_512 = MessageDigest.getInstance("SHA3-512");
	    } catch (NoSuchAlgorithmException e1) {
	      e1.printStackTrace();
	    }
	    byte[] sha3_256_result = sha3_512.digest(this.code.getBytes());
	    String s = String.format("%040x", new BigInteger(1, sha3_256_result));
	    
	    //ハッシュ値をprivate変数strHashCodeへ代入
	    this.strHashCode = s;
	    
	    return this.strHashCode;
	}
	
	@Override
	public String toString() {
		return this.strHashCode;
	}
 }
