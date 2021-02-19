package co.mydiary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiaryOracleDAO implements DAO {

	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;

	@Override
	public int insert(DiaryVO vo) {
		int r = 0;
		try {
			// 1. connect(연결)
			conn = JdbcUtil.connect();
			// 2. statement (구문)
			String sql = "INSERT INTO diary (wdate,  contents) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);

			// 3. execute(실행)
			pstmt.setString(1, vo.getWdate());
			pstmt.setString(2, vo.getContents());

			r = pstmt.executeUpdate();
			System.out.println(r + "건이 등록됨");

			// 4. resultset(select라면 조회결과처리)
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public void update(DiaryVO vo) {
		try {
			// 1. connect(연결)
			conn = JdbcUtil.connect();
			// 2. statement (구문)
			String sql = "UPDATE DIARY SET CONTENTS=? WHERE WDATE=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setString(2, vo.getWdate());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 수정됨");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
	}

	@Override
	public int delete(String date) {
		int r = 0;
		try {
			// 1. connect(연결)
			conn = JdbcUtil.connect();
			// 2. statement (구문)
			String sql = "DELETE FROM DIARY WHERE WDATE=?";
			pstmt = conn.prepareStatement(sql);

			// 3. execute(실행)
			pstmt.setString(1, date);
			r = pstmt.executeUpdate();
			System.out.println(r + "건이 삭제됨");
			// 4. resultset(select라면 조회결과처리)
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public DiaryVO selectDate(String date) {
		DiaryVO vo = new DiaryVO();
		try {
			// 1. connect(연결)
			conn = JdbcUtil.connect();
			// 2. statement (구문)
			String sql = "SELECT * FROM DIARY WHERE WDATE=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				vo.setWdate(rs.getString("WDATE"));
				vo.setContents(rs.getString("CONTENTS"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		return vo;
	}

	@Override
	public List<DiaryVO> selectContent(String content) {
		ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();
		try {
			conn = JdbcUtil.connect();
			String sql = "SELECT WDATE,CONTENTS FROM DIARY WHERE CONTENTS LIKE '%'||?||'%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content.trim());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DiaryVO vo = new DiaryVO();
				vo.setWdate(rs.getString("WDATE"));
				vo.setContents(rs.getString("CONTENTS"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

	@Override
	public List<DiaryVO> selectAll() {
		ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();
		try {
			conn = JdbcUtil.connect();
			String sql = "SELECT WDATE,CONTENTS FROM DIARY ORDER BY WDATE";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DiaryVO vo = new DiaryVO();
				vo.setWdate(rs.getString("WDATE"));
				vo.setContents(rs.getString("CONTENTS"));
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. close(연결해제)
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

}
