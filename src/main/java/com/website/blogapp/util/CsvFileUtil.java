package com.website.blogapp.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import com.opencsv.CSVWriter;
import com.website.blogapp.payload.PostDto;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CsvFileUtil {

	public void writePostToCsv(PrintWriter writer, List<PostDto> postDto) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Define format
		CSVWriter csvWriter = new CSVWriter(writer);

		// Writing Header
		String header[] = { "postId", "postTitle", "postContent", "postImageName", "postCreatedDate", "CategoryTitle" };

		csvWriter.writeNext(header);

		// Writing data
		for (PostDto post : postDto) {
			String data[] = { String.valueOf(post.getPostId()), post.getPostTitle(), post.getPostContent(),
					post.getPostImageName(), "\"" + sdf.format(post.getPostCreatedDate()) + "\"",
					post.getCategory().getCategoryTitle() };

			csvWriter.writeNext(data);
		}

		csvWriter.close();
	}

}
