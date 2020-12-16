	public interface IDao{
		//==> XML 에서 sql문을 이용해서 데이터를 DB에서 가져오고 값을 전달<==
		public ArrayList<BDto> list( );
		public memberCheck(String userid,String passcode);
		public write(String bName,String bTitle,String bContent);
		public contentView(int bId);
		public modify(int bId,String bName , String bTitle, String bContent);
		public delete(int bId);
		public upHit(int bId);
	}