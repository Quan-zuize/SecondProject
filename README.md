# Second_Project
Simple pacman game code in Netbeans
-Phần chính giữa là 1 ma trận mô tả tọa độ cho nhân vật,khoảng trống có thể di chuyển,vật cản và chấm điểm(dot)...Trong khi chơi game bấm vào các nút.
-Phần phía dưới sẽ hiển thị số điểm đang có, level hiện tại
 và số lần chơi còn lại trong 1 lượt.
-Sau khi hoàn thành level hiện tại sẽ chuyển sang level khác(chuyển sang map khác)
Chức năng “rank” chức năng xem thành tích kỷ lục:
-Chương trình mở giao diện để xem xếp hạng của 3 người chơi có thành tích (gồm điểm và thời gian) vượt trội nhất và tên của họ.
-Thông tin được sắp xếp theo thứ tự điểm từ cao đến thấp
-Dữ liệu xếp hạng này sẽ được lưu trong cơ sở dữ liệu của hệ thống,khi chức năng được gọi đến sẽ load thông tin từ cơ sở dữ liệu và hiển thị lên giao diện.
-Mỗi khi kết thúc một lượt chơi game,hệ thống sẽ kiểm tra điểm và thời gian của người chơi,nếu thành tích vượt trội hơn ít nhất 1 người trong top 3,hệ thống sẽ xóa thông tin của người đứng đầu top 3 và bắt đầu nhập thông tin mới của người chơi để lưu thành tích.
