//このソースは、VstoneMagicによって自動生成されました。
//ソースの内容を書き換えた場合、VstoneMagicで開けなくなる場合があります。
package	jp.co.mysota;
import	main.main.GlobalVariable;
import	jp.vstone.RobotLib.*;
import	jp.vstone.sotatalk.*;
import	jp.vstone.sotatalk.SpeechRecog.*;
import	jp.vstone.network.*;
import	com.google.gson.Gson;
import	java.awt.Color;
import	java.io.BufferedReader;
import	java.io.InputStreamReader;
import	java.net.HttpURLConnection;
import	java.net.URL;

public class mymain
{

	public String speechRecogResult;
	public RecogResult recogresult;
	public CRobotPose pose;
	public mymain()																										//@<BlockInfo>jp.vstone.block.func.constructor,32,32,288,32,False,4,@</BlockInfo>
	{
																														//@<OutputChild>
		/*String speechRecogResult*/;																					//@<BlockInfo>jp.vstone.block.variable,96,32,96,32,False,3,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*RecogResult recogresult*/;																					//@<BlockInfo>jp.vstone.block.variable,160,32,160,32,False,2,break@</BlockInfo>
																														//@<EndOfBlock/>
		/*CRobotPose pose*/;																							//@<BlockInfo>jp.vstone.block.variable,224,32,224,32,False,1,break@</BlockInfo>
																														//@<EndOfBlock/>
																														//@</OutputChild>
	}																													//@<EndOfBlock/>

	//@<Separate/>
	public void main()																									//@<BlockInfo>jp.vstone.block.func,32,192,1232,192,False,19,コメント@</BlockInfo>
	throws SpeechRecogAbortException {
		if(!GlobalVariable.TRUE) throw new SpeechRecogAbortException("default");

																														//@<OutputChild>
		GlobalVariable.sotawish.Say((String)"こんにちは。私はいまAIサービスと接続されています。目が青く光った時に話しかけてもらえれば、聞き取った単語からAIが連想した文章を発話します。",MotionAsSotaWish.MOTION_TYPE_TALK,(int)11,(int)13,(int)11);	//@<BlockInfo>jp.vstone.block.talk.say,96,192,96,192,False,18,@</BlockInfo>
																														//@<EndOfBlock/>
		while(GlobalVariable.TRUE)																						//@<BlockInfo>jp.vstone.block.while.endless,192,320,1136,320,False,17,Endless@</BlockInfo>
		{

																														//@<OutputChild>
			recogresult = GlobalVariable.recog.getRecognitionwithAbort((int)60000);										//@<BlockInfo>jp.vstone.block.talk.speechrecog.regex2,272,272,1072,272,False,16,音声認識を行い、結果を条件に正規表現文字列で比較する。認識スコアが一番高い結果に分岐する。実際に認識された文字列はspeechRecogResultに代入される@</BlockInfo>
			speechRecogResult = recogresult.CheckBest(new String[]{
			 ".*終わり.*" ,  "" , 
			},true);
			if(speechRecogResult == null) speechRecogResult = "";

			if(speechRecogResult.contains((String)".*終わり.*"))
			{
				speechRecogResult = recogresult.getBasicResult();
				if(speechRecogResult == null) speechRecogResult = "";

																														//@<OutputChild>
					GlobalVariable.sotawish.Say((String)"終了します。",MotionAsSotaWish.MOTION_TYPE_TALK,(int)11,(int)13,(int)11);	//@<BlockInfo>jp.vstone.block.talk.say,400,272,400,272,False,6,@</BlockInfo>
																															//@<EndOfBlock/>
					break;																									//@<BlockInfo>jp.vstone.block.break,464,272,464,272,False,5,break@</BlockInfo>	@<EndOfBlock/>
																																//@</OutputChild>

			}
			else
			{
				speechRecogResult = recogresult.getBasicResult();
				if(speechRecogResult == null) speechRecogResult = "";

																														//@<OutputChild>
					if(!GlobalVariable.sotawish.isPlayIdling()) GlobalVariable.sotawish.StartIdling();						//@<BlockInfo>jp.vstone.block.talk.idling2,336,368,976,368,False,15,Idling開始@</BlockInfo>
					try{
					
					
																															//@<OutputChild>
						int code=0;																							//@<BlockInfo>jp.vstone.block.variable,400,368,400,368,False,14,break@</BlockInfo>
																															//@<EndOfBlock/>
						String inputWord=null;																				//@<BlockInfo>jp.vstone.block.variable,464,368,464,368,False,13,break@</BlockInfo>
																															//@<EndOfBlock/>
						String outString=null;																				//@<BlockInfo>jp.vstone.block.variable,528,368,528,368,False,12,break@</BlockInfo>
																															//@<EndOfBlock/>
						speechRecogResult = GlobalVariable.recog.getResponsewithAbort((int)60000,(int)3);					//@<BlockInfo>jp.vstone.block.talk.speechrecog.get,592,368,592,368,False,20,音声認識して、得られた結果（文字列）をspeechRecogResultに代入します。@</BlockInfo>
						if(speechRecogResult == null) speechRecogResult = "";
					
																															//@<EndOfBlock/>
						inputWord=(String)speechRecogResult;																//@<BlockInfo>jp.vstone.block.calculater,656,368,656,368,False,11,@</BlockInfo>
																															//@<EndOfBlock/>
						CRobotUtil.Log(getClass().getSimpleName(), (String)inputWord);										//@<BlockInfo>jp.vstone.block.printlog,720,368,720,368,False,10,@</BlockInfo>	@<EndOfBlock/>
							String urlString = "https://api.a3rt.recruit-tech.co.jp/text_suggest/v2/predict?apikey=gJbzWtsVxuhWkCkP3gkuxs2OHL7nf5hG&previous_description=" + inputWord;	//@<BlockInfo>jp.vstone.block.freeproc,784,368,784,368,False,9,@</BlockInfo>
					
							String result = "";
					
							try{
							    URL url = new URL(urlString);
							    HttpURLConnection con = (HttpURLConnection) url.openConnection();
							    con.connect();
					
							    BufferedReader in = new BufferedReader(new InputStreamReader(
							    con.getInputStream()));
							    String tmp = "";
							    while ((tmp = in.readLine()) != null) {
							        result += tmp;
							    }
							    in.close();
							    con.disconnect();
							}catch(Exception e){
							    e.printStackTrace();
							}
					
							if (result != null){
							    int beginIndex = result.indexOf("\"suggestion\": [\"");
							    beginIndex = beginIndex + 15;
							    result = result.substring(beginIndex);
							    int endIndex = result.indexOf("]}");
							    result = result.substring(0,endIndex);
							    String[] results = result.split(",", 0);
							    outString = inputWord + results[0].replace("\"","") + inputWord + results[1].replace("\"","");
							}
																															//@<EndOfBlock/>
						CRobotUtil.Log(getClass().getSimpleName(), (String)outString);										//@<BlockInfo>jp.vstone.block.printlog,848,368,848,368,False,8,@</BlockInfo>	@<EndOfBlock/>
						GlobalVariable.sotawish.Say((String)outString,MotionAsSotaWish.MOTION_TYPE_TALK,(int)11,(int)13,(int)11);	//@<BlockInfo>jp.vstone.block.talk.say,912,368,912,368,False,7,@</BlockInfo>
																															//@<EndOfBlock/>
																															//@</OutputChild>
					
					
					}
					finally
					{
						GlobalVariable.sotawish.StopIdling();
					}
																															//@<EndOfBlock/>
																																//@</OutputChild>

			}
																														//@<EndOfBlock/>
																														//@</OutputChild>
		}
																														//@<EndOfBlock/>
																														//@</OutputChild>

	}																													//@<EndOfBlock/>

}
