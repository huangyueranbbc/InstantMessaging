import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;


/**
 * @author 黄跃然 
 * 注:API error. code=1503, message=NAME_EXIST, responseCode=453
 * 此异常时因为detect脸过后，遍历add person name, 和id,换一张图片可能有问题，
 * 因为脸变了，对应已经注册了的名字就会出现错误，得改名字。
 */
public class Test {

	public static void main(String[] args) {
		//replace api_key and api_secret here (note)
		HttpRequests httpRequests = new HttpRequests("4480afa9b8b364e30ba03819f3e9eff5", "Pz9VFT8AP3g_Pz8_dz84cRY_bz8_Pz8M ", true, true);
		
		JSONObject result = null;
		
		try {
			
			System.out.println(Charset.forName("UTF-8").name());
		
			System.out.println("FacePlusPlus API Test:");
			
			//detection/detect
			result = httpRequests.detectionDetect(new PostParameters().setUrl("http://cn.faceplusplus.com/wp-content/themes/faceplusplus/assets/img/demo/9.jpg"));
			System.out.println(result);
			
			System.out.println(result.getJSONArray("face").getJSONObject(0).getJSONObject("position").getJSONObject("center"));
			
			//-----------------Person-----------------
			//person/create
			System.out.println("\nperson/create");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				System.out.println(httpRequests.personCreate(new PostParameters().setPersonName("person___:"+i)));
			
			new PostParameters().setPersonName("person___:"+0).setFaceId(
					result.getJSONArray("face").getJSONObject(0).getString("face_id")).getMultiPart().writeTo(System.out);
			
			//person/add_face
			System.out.println("\nperson/add_face");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				System.out.println(httpRequests.personAddFace(new PostParameters().setPersonName("person___:"+i).setFaceId(
						result.getJSONArray("face").getJSONObject(i).getString("face_id"))));
			
			//person/set_info
			System.out.println("\nperson/set_info");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i) {
				new PostParameters().setPersonName("person___:"+i).setTag("中文 tag_"+i).getMultiPart().writeTo(System.out);
				System.out.println(httpRequests.personSetInfo(new PostParameters().setPersonName("person___:"+i).setTag("中文 tag_"+i)));
			}
			
			//person/get_info
			System.out.println("\nperson/get_info");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				System.out.println(httpRequests.personGetInfo(new PostParameters().setPersonName("person___:"+i)));
			
			//-----------------Faceset-----------------
			//faceset/create
			System.out.println("\nfaceset/create");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				System.out.println(httpRequests.facesetCreate(new PostParameters().setFacesetName("faceset___:"+i)));
			
			//faceset/add_face
			System.out.println("\nfaceset/add_face");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				System.out.println(httpRequests.facesetAddFace(new PostParameters().setFacesetName("faceset___:"+i).setFaceId(
						result.getJSONArray("face").getJSONObject(i).getString("face_id"))));
			
			//faceset/set_info
			System.out.println("\nfaceset/set_info");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i) {
				new PostParameters().setFacesetName("faceset___:"+i).setTag("中文 tag_"+i).getMultiPart().writeTo(System.out);
				System.out.println(httpRequests.facesetSetInfo(new PostParameters().setFacesetName("faceset___:"+i).setTag("中文 tag_"+i)));
			}
			
			//faceset/get_info
			System.out.println("\nfaceset/get_info");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				System.out.println(httpRequests.facesetGetInfo(new PostParameters().setFacesetName("faceset___:"+i)));
			
			
			
			//-----------------Group-----------------
			//group/create
			System.out.println("\ngroup/create");
			System.out.println(httpRequests.groupCreate(new PostParameters().setGroupName("group___:0")));
			
			//group/add_person
			System.out.println("\ngroup/add_person");
			ArrayList<String> personList = new ArrayList<String>();
			for (int i = 0; i < result.getJSONArray("face").length(); ++i)
				personList.add("person___:"+i);
			
			new PostParameters().setGroupName("group___:0").setPersonName(personList).getMultiPart().writeTo(System.out);
			System.out.println(httpRequests.groupAddPerson(new PostParameters().setGroupName("group___:0").setPersonName(personList)));
			
			//group/set_info
			System.out.println("\ngroup/set_info");
			System.out.println(httpRequests.groupSetInfo(new PostParameters().setGroupName("group___:0").setTag("group tag")));
			
			//group/get_info
			System.out.println("\ngroup/get_info");
			System.out.println(httpRequests.groupGetInfo(new PostParameters().setGroupName("group___:0")));
			
			
			//-----------------Recognition-----------------
			
			//recognition/train
			JSONObject syncRet = null; 
			
			System.out.println("\ntrain/Identify");
			syncRet = httpRequests.trainIdentify(new PostParameters().setGroupName("group___:0"));
			System.out.println(syncRet);
			System.out.println(httpRequests.getSessionSync(syncRet.getString("session_id")));
			
			System.out.println("\ntrain/verify");
			for (int i = 0; i < result.getJSONArray("face").length(); ++i) {
				syncRet = httpRequests.trainVerify(new PostParameters().setPersonName("person___:" + i));
				System.out.println(httpRequests.getSessionSync(syncRet.get("session_id").toString()));
			}
			
			//recognition/recognize
			System.out.println("\nrecognition/identify");
			System.out.println(httpRequests.recognitionIdentify(
					new PostParameters().setGroupName("group___:0").setUrl("http://cn.faceplusplus.com/wp-content/themes/faceplusplus/assets/img/demo/5.jpg")));
			
			//recognition/verify
			System.out.println("\nrecognition/verify");
			System.out.println(
					httpRequests.recognitionVerify(new PostParameters().setPersonName("person___:0").setFaceId(
							result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
			System.out.println(
					httpRequests.recognitionVerify(new PostParameters().setPersonName("person___:1").setFaceId(
							result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
			
			//-----------------Info-----------------
			//info/get_app
			System.out.println("\ninfo/get_app");
			System.out.println(httpRequests.infoGetApp());
			
			//info/get_face
			System.out.println("\ninfo/get_app");
			System.out.println(httpRequests.infoGetFace(new PostParameters().setFaceId(
					result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
			
			//info/get_group_list
			System.out.println("\ninfo/get_group_list");
			System.out.println(httpRequests.infoGetGroupList());
			
			//info/get_image
			System.out.println("\ninfo/get_image");
			System.out.println(httpRequests.infoGetImage(new PostParameters().setImgId(
					result.getString("img_id"))));
			
			//info/get_person___:list
			System.out.println("\ninfo/get_person___:list");
			System.out.println(httpRequests.infoGetPersonList());
			
			//info/get_quota
			System.out.println("\ninfo/get_quota");
			System.out.println(httpRequests.infoGetQuota());
			
			//info/get_session
			System.out.println("\ninfo/get_session");
			System.out.println(httpRequests.infoGetSession(new PostParameters().setSessionId(
					result.getString("session_id"))));
			
			
			//-----At last----
			//person/remove_face
			System.out.println("\nperson/remove_face");
			System.out.println(httpRequests.personRemoveFace(
					new PostParameters().setPersonName("person___:0").setFaceId(
							result.getJSONArray("face").getJSONObject(0).getString("face_id"))));
			
			//group/delete
			System.out.println("\ngroup/delete");
			System.out.println(httpRequests.groupDelete(new PostParameters().setGroupName("group___:0")));
			
			//person/delete
			System.out.println("\nperson/delete");
			System.out.println(httpRequests.personDelete(new PostParameters().setPersonName("person___:0")));
			
			//faceset/delete
			System.out.println("\nfaceset/delete");
			System.out.println(httpRequests.facesetDelete(new PostParameters().setFacesetName("faceset___:0")));
			 
		} catch(FaceppParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
		} finally {
			try {
				for (int i = 1; i < result.getJSONArray("face").length(); ++i) {
					httpRequests.personDelete(new PostParameters().setPersonName("person___:"+i));
					httpRequests.facesetDelete(new PostParameters().setFacesetName("faceset___:"+i));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (FaceppParseException e) {
				e.printStackTrace();
			}
		}
	}
}
