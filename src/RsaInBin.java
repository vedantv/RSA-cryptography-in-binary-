import java.math.BigInteger;
import java.util.Scanner;
public class RsaInBin {
		private final static BigInteger one = new BigInteger("1");
		   

		   private BigInteger privateKey;
		   private BigInteger publicKey;
		   private BigInteger modulus;
		   private String privatebin;
		   private String publicbin;

		   // generate an N-bit (roughly) public and private key
		   RsaInBin() {
			   
			   BigInteger p = new BigInteger("11");
			   BigInteger q = new BigInteger("13");

			  //   BigInteger p = new BigInteger("8226300218526427578904608583398443065691488851285857053707420239647980600720044648521159983497369940193924807016312926942965941829100238436520621194830502057679984311048901859684958163286269801735080727963463944945290287834821564034145405855084108116324533235715989734728965234314460156650900656780563");
			  //   BigInteger q = new BigInteger("1152574679987110855605944026722015849292700666320393004202728793244603273319049247166285597617666829774938557558250999526515429413099644164319913699521607551349950515792522695636562052967210129087319503010037376653099872048708732873490447854221516624205170828371900017753916468410494853998172895848339");

			   
		      //BigInteger p = new BigInteger("63697977222409713869437097304671093262295012529600069909066304723455697547013");
		      //BigInteger q = new BigInteger("79553522712930561741266391652589754305856384183282833774564298065898707372879");
		     
			   
			   
			   // BigInteger p = BigInteger.valueOf(139);
		      //BigInteger q = BigInteger.valueOf(119);
			   
			   BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
		      
		      System.out.println("prime nos are \n p= "+p+"\n & q="+q);

		      modulus    = p.multiply(q);
		      publicKey = one;
		      BigInteger x = new BigInteger("2");
		      while(x.compareTo(one)==1)
		      {  
		    	  publicKey = publicKey.add(one);
		    	  x = phi.gcd(publicKey);
		      }
		      //publicKey  = BigInteger.valueOf(65537);     // common value in practice = 2^16 + 1
		      privateKey = publicKey.modInverse(phi);
		      
		      publicbin = publicKey.toString(2);
		      privatebin = privateKey.toString(2);
		   }


		   BigInteger encrypt(BigInteger message) {
		      int i = 0;
		      BigInteger ctext = new BigInteger("1");
		      BigInteger sq = new BigInteger("2");
		      System.out.println("publicbin is "+publicbin);

		      while(i<publicbin.length())
		      {
		    	  if(publicbin.charAt(i) == '1')
		    		  ctext = (message.multiply(ctext.modPow(sq,modulus))).mod(modulus);
		    	  else if(publicbin.charAt(i) == '0')
		    		  ctext = ctext.modPow(sq,modulus);
		    	  i++;
		    		  
		      } 
			   return ctext;
		   }

		   BigInteger decrypt(BigInteger encrypted) {
			   
			      
			      int i;
			      BigInteger sq = new BigInteger("2");
			      BigInteger a,j;
			      a = one;
			      for(j=one;j.compareTo(privateKey)==-1;j=j.add(one))
			      {
			    	  i=0;
			    	  BigInteger dprevtext = one;
			    	  
			    	  String jbin = j.toString(2);
			    	  
			    	  
			    	  while(i<jbin.length())
				      {
				    	  if(jbin.charAt(i) == '1')
				    		  dprevtext = (encrypted.multiply(dprevtext.modPow(sq,modulus))).mod(modulus);
				    	  else if(jbin.charAt(i) == '0')
				    		  dprevtext = dprevtext.modPow(sq,modulus);
				    	  
				    	  i++;
				      }
			    	  
			    	  if (dprevtext.compareTo(one)==0)
			    	  {
			    		  a = j;
			    		  break;
			    	  }
			    	  else
			    		  a = one;
			      }
			      privateKey = privateKey.mod(a);
			      privatebin = privateKey.toString(2);
			      int k=0;
			      BigInteger dtext = one;
			      while(k<privatebin.length())
			      {
			    	  if(privatebin.charAt(k) == '1')
			    		  dtext = (encrypted.multiply(dtext.modPow(sq,modulus))).mod(modulus);
			    	  else if(privatebin.charAt(k) == '0')
			    		  dtext = dtext.modPow(sq,modulus);
			    	  k++;	  
			      } 
				   return dtext;
		   }
		   
		   public static BigInteger toAscii(char s){
		        StringBuilder sb = new StringBuilder();
		        String ascString = null;
		        BigInteger asciiInt;
		                    sb.append((int)s);
		                ascString = sb.toString();
		                asciiInt = new BigInteger(ascString);
		                return asciiInt;
		    }


		   public String toString() {
		      String s = "";
		      s += "public  = " + publicKey  + "\n";
		      s += "private = " + privateKey + "\n";
		      s += "modulus = " + modulus;
		      return s;
		   }
		 
		   public static void main(String[] args) {
			   RsaInBin key = new RsaInBin();
		      System.out.println(key);
		 
		      // create random message, encrypt and decrypt
		      //BigInteger message = new BigInteger(N-1, random);
		      //// create message by converting string to integer
		      String s = "ab";
		      for(int i=0;i<s.length();i++)
		      	{
		    	  BigInteger message = toAscii(s.charAt(i));
		    	  BigInteger encrypt = key.encrypt(message);
		    	  BigInteger decrypt = key.decrypt(encrypt);
		    	  System.out.println("message   = " + message);
		    	  System.out.println("encrypted = " + encrypt);
		    	  System.out.println("decrypted = " + decrypt);
		      	}
		   }

}
