/****************************************/
/*	Program Name:		Lab1.java		*/
/*							*/
/*	Student Name:		Sameep Shah		*/
/*	Semester:			Spring 2023	*/
/*	Class Section:	    COSC20203-055		*/
/*	Instructor:			Dr. Rinewalt	*/
/*							*/
/*	Program Overview:	Lab #1			*/
/*							*/
/*	Input: 						*/
/* 	Instructions in Assembly or binary		*/
/*  or Hex						*/
/*	Output:						*/
/*  Encode/decode instructions from			*/
/* Assembly to binary/hex and vice versa		*/

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Lab1 extends JFrame implements ActionListener {
	static final long serialVersionUID = 1l;
	private JTextField assemblerInstruction;
	private JTextField binaryInstruction;
	private JTextField hexInstruction;
	private JLabel errorLabel;
	
	public Lab1() {
		setTitle("XDS Sigma 9");
		setBounds(100, 100, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// SET UP THE ASSEMBLY LANGUAGE TEXTFIELD AND BUTTON
		assemblerInstruction = new JTextField();
		assemblerInstruction.setBounds(25, 24, 134, 28);
		getContentPane().add(assemblerInstruction);

		JLabel lblAssemblyLanguage = new JLabel("Assembly Language");
		lblAssemblyLanguage.setBounds(30, 64, 160, 16);
		getContentPane().add(lblAssemblyLanguage);

		JButton btnEncode = new JButton("Encode");
		btnEncode.setBounds(200, 25, 117, 29);
		getContentPane().add(btnEncode);
		btnEncode.addActionListener(this);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// SET UP THE BINARY INSTRUCTION TEXTFIELD AND BUTTON
		binaryInstruction = new JTextField();
		binaryInstruction.setBounds(25, 115, 330, 28);
		getContentPane().add(binaryInstruction);

		JLabel lblBinary = new JLabel("Binary Instruction");
		lblBinary.setBounds(30, 155, 190, 16);
		getContentPane().add(lblBinary);

		JButton btnDecode = new JButton("Decode Binary");
		btnDecode.setBounds(200, 150, 150, 29);
		getContentPane().add(btnDecode);
		btnDecode.addActionListener(this);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// SET UP THE HEX INSTRUCTION TEXTFIELD AND BUTTON
		hexInstruction = new JTextField();
		hexInstruction.setBounds(25, 220, 134, 28);
		getContentPane().add(hexInstruction);

		JLabel lblHexEquivalent = new JLabel("Hex Instruction");
		lblHexEquivalent.setBounds(30, 260, 131, 16);
		getContentPane().add(lblHexEquivalent);

		JButton btnDecodeHex = new JButton("Decode Hex");
		btnDecodeHex.setBounds(200, 220, 150, 29);
		getContentPane().add(btnDecodeHex);
		btnDecodeHex.addActionListener(this);		
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// SET UP THE LABEL TO DISPLAY ERROR MESSAGES
		errorLabel = new JLabel("");
		errorLabel.setBounds(25, 320, 280, 16);
		getContentPane().add(errorLabel);
	}

	public void actionPerformed(ActionEvent evt) {
		errorLabel.setText("");
		if (evt.getActionCommand().equals("Encode")) {
			encode();
		} else if (evt.getActionCommand().equals("Decode Binary")) {
			decodeBin();
		} else if (evt.getActionCommand().equals("Decode Hex")) {
			decodeHex();
		}
	}

	public static void main(String[] args) {
		Lab1 window = new Lab1();
		window.setVisible(true);
	}

// USE THE FOLLOWING METHODS TO CREATE A STRING THAT IS THE
// BINARY OR HEX REPRESENTATION OF A SORT OR INT

// CONVERT AN INT TO 8 HEX DIGITS
	String displayIntAsHex(int x) {
		String ans="";
		for (int i=0; i<8; i++) {
			int hex = x & 15;
			char hexChar = "0123456789ABCDEF".charAt(hex);
			ans = hexChar + ans;
			x = (x >> 4);
		}
		return ans;
	}

// CONVERT AN INT TO 32 BINARY DIGITS
	String displayIntAsBinary(int x) {
		String ans="";
		for(int i=0; i<32; i++) {
			ans = (x & 1) + ans;
			x = (x >> 1);
		}
		return ans;
	}
	
/************************************************************************/
/* Put your implementation of the encode, decodeBin, and decodeHex      */
/* methods here. You may add any other methods that you think are       */
/* appropriate. However, you MUST NOT change anything in the code       */
/* that I have written.                                                 */
/************************************************************************/
//Display a long variable in binary	
	String displayLongAsBinary(long x) {
		String ans="";
		for(int i=0; i<32; i++) {
			ans = (x & 1) + ans;
			x = (x >> 1);
		}
		return ans;
	}
//Display a long variable in hex
	String displayLongAsHex(long x) {
		String ans="";
		for (int i=0; i<8; i++) {
			long hex = x & 15;
			char hexChar = "0123456789ABCDEF".charAt((int)hex);
			ans = hexChar + ans;
			x = (x >> 4);
		}
		return ans;
	}
	
	void encode() {
		boolean out = true;
		//Getting input from user in string (assembly instruction)
		String asl = assemblerInstruction.getText();
		//answer variable, used long as the answers we are dealing with are out of range for int
		long ans = 0;
		//dividing the inputs using substring() method
		String comm1 = asl.substring(0,asl.indexOf(','));
		if(comm1.equals("LI")) {
			ans = ans | 0x22000000;
		} else if(comm1.equals("LW")){
			ans = ans | 0x32000000;
		} else if(comm1.equals("AW")) {
			ans = ans | 0x30000000;
		} else if(comm1.equals("STW")) {
			ans = ans | 0x35000000;
		}
		//finding the value of R and using OR binary operator to add it to ans
		try {
			String r = asl.substring(asl.indexOf(',')+1, asl.indexOf(' '));
			int rint = Integer.parseInt(r);
			if(rint>=0 && rint<=15) {
				asl = asl.substring(asl.indexOf(" "));
				asl = asl.trim();
				rint = rint << 20;
				ans = ans | rint;
			} else {
				out = false;
				errorLabel.setText("ERROR in R");
			}
		} catch (NumberFormatException nfe) {
			//using a try catch statement to catch illegal inputs from user
			errorLabel.setText("ERROR in R");
			out = false;
		}
		//main if else statement for digging deep into the input from the user
		if(asl.charAt(0)!='*' && asl.indexOf("*")==-1) {
			if(comm1.equals("LI")) {
				String v = asl;
				try {
					int vint = Integer.parseInt(v);
					if(vint>=-524288 && vint<=524287) {
						if(vint<0) {
							vint = ~vint;
							vint+=1;
							vint*=-1;
							vint = vint & 0x000FFFFF;
						}
						ans = ans | vint;
					} else {
						errorLabel.setText("ERROR in V");
						out = false;
					}
				} catch(NumberFormatException nfe) {
					errorLabel.setText("ERROR in V");
					out = false;
				}
			} else if(comm1.equals("LW") || comm1.equals("AW") || comm1.equals("STW")) {
				if(asl.indexOf(",")==-1) {
					String d = asl;
					try {
						int dint = Integer.parseInt(d);
						if(dint>=0 && dint<=131071) {
							ans = ans | dint;
						} else {
							errorLabel.setText("ERROR in D");
							out = false;
						}
						//binaryInstruction.setText(displayLongAsBinary(ans));
					} catch (NumberFormatException nfe){
						errorLabel.setText("ERROR in D");
						out = false;
					}
				} else {
					String d = asl.substring(0, asl.indexOf(","));
					try {
						int dint = Integer.parseInt(d);
						if(dint>=0 && dint<=131071) {
							ans = ans | dint;
						} else {
							errorLabel.setText("ERROR in D");
							out = false;
						}
					} catch (NumberFormatException nfe){
						errorLabel.setText("ERROR in D");
						out = false;
					}
					String x = asl.substring(asl.indexOf(",")+1);
					try {
						int xint = Integer.parseInt(x);
						if(xint>=1 && xint<=7) {
							xint = xint << 17;
							ans = ans | xint;
							binaryInstruction.setText(displayLongAsBinary(ans));
						} else {
							errorLabel.setText("ERROR in X");
							out = false;
						}
					} catch(NumberFormatException nfe) {
						errorLabel.setText("ERROR in X");
						out = false;
					}
				}
			}
		} else if(asl.charAt(0)=='*') {
			//checking if there is an asterisk in the input
			ans = ans | 0x80000000;
			asl = asl.substring(1);
			if(comm1.equals("LW") || comm1.equals("AW") || comm1.equals("STW")) {
				if(asl.indexOf(",")==-1) {
					String d = asl;
					try {
						int dint = Integer.parseInt(d);
						if(dint>=0 && dint<=131071) {
							ans = ans | dint;
						} else {
							out = false;
							errorLabel.setText("ERROR in D");
						}
						//binaryInstruction.setText(displayLongAsBinary(ans));
					} catch (NumberFormatException nfe){
						errorLabel.setText("ERROR in D");
						out = false;
					}
				} else {
					String d = asl.substring(0, asl.indexOf(","));
					try {
						int dint = Integer.parseInt(d);
						if(dint>=0 && dint<=131071) {
							ans = ans | dint;
						} else {
							errorLabel.setText("ERROR in D");
							out = false;
						}
					} catch (NumberFormatException nfe){
						errorLabel.setText("ERROR in D");
						out = false;
					}
					String x = asl.substring(asl.indexOf(",")+1);
					try {
						int xint = Integer.parseInt(x);
						if(xint>=1 && xint<=7) {
							xint = xint << 17;
							ans = ans | xint;
							//binaryInstruction.setText(displayLongAsBinary(ans));
						} else {
							errorLabel.setText("ERROR in X");
							out = false;
						}
					} catch(NumberFormatException nfe) {
						errorLabel.setText("ERROR in X");
						out = false;
					}
				}
			}
		}
		//output to GUI, only if boolean value of out is true, otherwise clear the text fields and say "ERROR"
		if(out==true) {
			binaryInstruction.setText(displayLongAsBinary(ans));
			hexInstruction.setText(displayLongAsHex(ans));
		} else {
			binaryInstruction.setText("");
			hexInstruction.setText("");
		}
	}

	void decodeBin() {
		//getting string input from user (binary)
		String inst = binaryInstruction.getText();
		//if length of binary string input is not 32 bits then giving out ERROR
		if(inst.length()!=32) {
			errorLabel.setText("ERROR in Binary Instruction");
		} else {
		try {
			String ans = null;
			//using parseUnsignedInt as parseInt() will result in an int where overflow is possible
			int i = Integer.parseUnsignedInt(inst, 2);
			//checking if there is an asterisk or not in the input
			int ast = i & 0x80000000;
			ast = ast >>> 31;
			//finding first command of the input "LI" or "LW" and so on...
			int comm1 = i & 0x7F000000;
			comm1 = comm1 >>> 24;
			//finding value of R
			int r = i & 0x00F00000;
			r = r >>> 20;
			//finding value of X, not necessarily used as some commands dont have X
			int x = i & 0x000E0000;
			x = x >>> 17;
			//finding value of D
			int d = i & 0x0001FFFF;
			if(comm1 == 0x22) {
				ans = "LI," + r + " ";
				//finding sign of V, so we can change the output accordingly
				int signv = i & 0x00080000;
				signv = signv >>> 19;
				int v = i & 0x000FFFFF;
				if(signv == 1) {
					v = ~v;
					v +=1;
					v -= 0xFFF00000;
					ans+= -v;
				} else {
					ans+= v;
				}
			} else if(comm1 == 0x32) {
				ans = "LW," + r;
				if(ast==1) {
					ans+=" *";
				} else {
					ans+=" ";
				}
				ans+=d;
				//adding crucial bug fix for not allowing X to be 0
				if(x!=0) {
					ans+=","+ x;
				}
			} else if(comm1 == 0x30) {
				ans = "AW," +r;
				if(ast==1) {
					ans+= " *";
				} else {
					ans+=" ";
				}
				ans+=d;
				if(x!=0) {
					ans += "," +x;
				}
			} else if(comm1 == 0x35) {
				ans = "STW,"+r;
				if(ast==1) {
					ans+= " *";
				} else {
					ans+=" ";
				}
				ans+=d;
				if(x!=0) {
					ans+=","+x;
				}
			} else {
				errorLabel.setText("ERROR in Binary Instruction");
			}
			assemblerInstruction.setText(ans);
			hexInstruction.setText(displayIntAsHex(i));
		} catch(NumberFormatException nfe) {
			errorLabel.setText("ERROR (Illegal input)");
			assemblerInstruction.setText("");
			hexInstruction.setText("");
		}
		}
	}

	void decodeHex() {
		//getting instructions from user in Hex
		String ins = hexInstruction.getText();
		try {
		//parsing hex to int as Unsigned to not overflow
		int num = Integer.parseUnsignedInt(ins, 16);
		//displaying int as binary using the method as given
		String binnum = displayIntAsBinary(num);
		//binaryInstruction.setText(binnum);
		//Answer String
		String ans = null;
		//Same code as decodeBinary
		int i = Integer.parseUnsignedInt(binnum, 2);
		int ast = i & 0x80000000;
		ast = ast >>> 31;
		int comm1 = i & 0x7F000000;
		comm1 = comm1 >>> 24;
		int r = i & 0x00F00000;
		r = r >>> 20;
		int x = i & 0x000E0000;
		x = x >>> 17;
		int d = i & 0x0001FFFF;
		if(comm1 == 0x22) {
			ans = "LI," + r + " ";
			int signv = i & 0x00080000;
			signv = signv >>> 19;
			int v = i & 0x000FFFFF;
			if(signv == 1) {
				v = ~v;
				v +=1;
				v -= 0xFFF00000;
				ans+= -v;
			} else {
				ans+= v;
			}
		} else if(comm1 == 0x32) {
			ans = "LW," + r;
			if(ast==1) {
				ans+=" *";
			} else {
				ans+=" ";
			}
			ans+=d;
			if(x!=0) {
				ans+=","+ x;
			}
		} else if(comm1 == 0x30) {
			ans = "AW," +r;
			if(ast==1) {
				ans+= " *";
			} else {
				ans+=" ";
			}
			ans+=d;
			if(x!=0) {
				ans += "," +x;
			}
		} else if(comm1 == 0x35) {
			ans = "STW,"+r;
			if(ast==1) {
				ans+= " *";
			} else {
				ans+=" ";
			}
			ans+=d;
			if(x!=0) {
				ans+=","+x;
			}
		} else {
			errorLabel.setText("ERROR in Hex Instruction");
		}
		assemblerInstruction.setText(ans);
		binaryInstruction.setText(binnum);
		} catch(NumberFormatException nfe) {
			errorLabel.setText("ERROR (Illegal input)");
			assemblerInstruction.setText("");
			binaryInstruction.setText("");
		}
		
	}
}
