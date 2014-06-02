package com.micar.google.analytics;

public class Constants
{
	public final static String AppType_FULL = "full";
	public final static String AppName = "MiCar";

	public final static String EVENT_CATEGORY_CAMERA_EVENT = "Camera";
	public final static String EVENT_CATEGORY_GALLERY_EVENT = "Gallery";

	//event

	public final static String EVENT_TAP = "tap";
	public final static String EVENT_SWIPE = "swipe";
	public final static String EVENT_HOLD_PRESS = "hold_press";
	public final static String EVENT_PINCH = "pinch";

	public final static String EVENT_MEDIA_INSERT = "db_media_insert";
	public final static String EVENT_MEDIA_UPDATE = "db_media_update";
	public final static String EVENT_MEDIA_DELETE = "db_media_delete";

	public final static String EVENT_MEDIA_KEYWORD_INSERT = "db_media_keyword_insert";
	public final static String EVENT_MEDIA_DATE_TAG_UPDATE = "db_media_date_tag_update";
	public final static String EVENT_MEDIA_DATE_TAG_MULTIPLE_UPDATE = "db_multiple_media_date_tag_update";
	public final static String EVENT_MEDIA_DATE_TAG_RESET = "db_media_date_tag_reset";
	public final static String EVENT_MEDIA_DATE_TAG_MULTIPLE_RESET = "db_multiple_media_date_tag_reset";

	public final static String EVENT_MEDIA_KEYWORD_UPDATE = "db_media_keyword_update";
	public final static String EVENT_MEDIA_KEYWORD_DELETE = "db_media_keyword_delete";

	//camera action strings

	public final static String EVENT_ACTION_LAUNCH_CUSTOM_CAMERA_SETTINGS = "launch_camera_settings";
	public final static String EVENT_ACTION_LAUNCH_VOICETAG_CAMERA_SETTINGS = "launch_voice_tag_camera_settings";
	public final static String EVENT_ACTION_LAUNCH_SPEECH_TO_TEXT = "speech_to_text";

	public final static String EVENT_ACTION_CAMERA_ZOOM_SEEKBAR = "zoom_changed";
	public final static String EVENT_ACTION_OPEN_CAMERA = "open_camera";
	public final static String EVENT_ACTION_EXIT_CAMERA = "exit_camera";
	public final static String EVENT_ACTION_TOGGLE_CAMERA_MODE_VIDEO = "toggle_camera_to_video_recording";
	public final static String EVENT_ACTION_TOGGLE_CAMERA_MODE_IMAGE = "toggle_camera_to_image_capture";
	public final static String EVENT_ACTION_TOGGLE_CAMERA_FRONT = "toggle_camera_to_front";
	public final static String EVENT_ACTION_TOGGLE_CAMERA_BACK = "toggle_camera_to_front";
	public final static String EVENT_ACTION_CAPTURE_IMAGE = "capture_image";
	public final static String EVENT_ACTION_CAPTURE_VIDEO_START = "capture_video_start";
	public final static String EVENT_ACTION_CAPTURE_VIDEO_END = "capture_video_end";
	public final static String EVENT_ACTION_TOGGLE_FLASH = "toggle_flash";
	public final static String EVENT_ACTION_CAMERA_FILTER = "camera_filter";
	public final static String EVENT_ACTION_ZOOM_IN_CAMERA = "camera_zoom_in";
	public final static String EVENT_ACTION_ZOOM_OUT_CAMERA = "camera_zoom_out";
	public final static String EVENT_ACTION_DELETE_MEDIA = "delete_media";
	public final static String EVENT_ACTION_DELETE_TAG = "delete_tag";
	public final static String EVENT_ACTION_DONE = "done";
	public final static String EVENT_ACTION_SHARE = "share";
	public final static String EVENT_ACTION_SET_AS = "set_as";
	public final static String EVENT_ACTION_VIEW_FULL_IMAGE = "custom_camera_thumb_full_view";
	public final static String EVENT_ACTION_PLAY_VIDEO = "play_video";
	public final static String EVENT_ACTION_GEO_TAG = "geo_tag";
	public final static String EVENT_ACTION_MENU_CUSTOM_CAMERA_SETTINGS = "custom_camera_settings";
	public final static String EVENT_ACTION_MENU_VOICE_TAG_CAMERA_SETTINGS = "camera_voice_tag_settings";
	public final static String EVENT_ACTION_CAMERA_KEYWORD_TAG_HELP_MESSAGE = "camera_help_message";

	public final static String EVENT_ACTION_DB_INSERT_MEDIA = "db_insert_media";
	public final static String EVENT_ACTION_DB_DELETE_MEDIA = "db_delete_media";
	public final static String EVENT_ACTION_DB_MULT_DELETE_MEDIA = "db_delete_multiple_media";
	public final static String EVENT_ACTION_DB_ADD_KEYWORD_MEDIA = "db_add_keyword_media";
	public final static String EVENT_ACTION_DB_MULT_ADD_KEYWORD_MEDIA = "db_add_keyword_multiple_media";
	public final static String EVENT_ACTION_DB_UPDATE_KEYWORD_MEDIA = "db_update_keyword_media";
	public final static String EVENT_ACTION_DB_DELETE_KEYWORD_MEDIA = "db_delete_keyword_media";
	public final static String EVENT_ACTION_DB_MULT_DELETE_KEYWORD_MEDIA = "db_delete_keyword_multiple_media";

	//	//camera settings
	//	public final static String EVENT_ACTION_CAMERA_SELF_TIMER = "camera_self_timer";
	//	public final static String EVENT_ACTION_CAMERA_ISO = "camera_ISO";
	//	public final static String EVENT_ACTION_CAMERA_ANTIBANDING = "camera_antinbanding";
	//	public final static String EVENT_ACTION_CAMERA_EXPOSURE_COMPENSATION = "camera_exposure";
	//	public final static String EVENT_ACTION_CAMERA_FOCUS_MODE = "camera_focus_mode";
	//	public final static String EVENT_ACTION_CAMERA_SCENE_MODE = "camera_scene_mode";
	//	public final static String EVENT_ACTION_CAMERA_WHITE_BALANCE = "camera_white_balance";
	//	public final static String EVENT_ACTION_CAMERA_RESOLUTION = "camera_resolution";
	//	public final static String EVENT_ACTION_CAMERA_REVIEW = "camera_review";
	//	public final static String EVENT_ACTION_CAMERA_GEO_TAG = "camera_geo_tag";
	//	public final static String EVENT_ACTION_CAMERA_RESET_TO_DEFAULT = "camera_reset_to_default";
	//
	//	//camera voicetag settings
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_TAGGING = "camera_tagging";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_KEYWORD_TAG = "camera_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_DATE_TAG = "camera_date_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_LOCK_KEYWORD_TAG_PHOTOS = "camera_lock_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_LOCK_DATE_TAG_PHOTOS = "camera_lock_date_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_LISTEN_FOR_KEYWORD_TAG_MIC = "camera_mic_listen_for_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_CONFIRM_KEYWORD_TAG = "camera_confirm_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_CONFIRM_KEYWORD_TAG_ON_EXIT = "camera_confirm_keyword_tag_on_exit_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_FORMER_KEYWORD_TAGS = "camera_former_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_REMOVE_ALL_FORMER_KEYWORD_TAGS = "camera_remove_all_former_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_RESET_TO_DEFULT = "camera_reset_to_default_voice_tag_settings";
	//	public final static String EVENT_ACTION_SETTINGS_CAMERA_RESET_TO_CUSTOM = "camera_reset_to_custom_voice_tag_settings";

	//gallery action strings
	public final static String EVENT_ACTION_LAUNCH_SEARCH_SPEECH_TO_TEXT = "speech_to_text_for_search";
	public final static String EVENT_ACTION_LAUNCH_TAG_SPEECH_TO_TEXT = "speech_to_text_for_tag";
	public final static String EVENT_ACTION_SELECT_MEDIA_TO_TAG = "select_media_to_tag";
	public final static String EVENT_ACTION_SELECT_MEDIA_TO_DELETE = "select_media_to_delete";
	public final static String EVENT_ACTION_LAUNCH_GALLERY_FULL_SCREEN_VIEW = "launch_gallery_full_screen";
	public final static String EVENT_ACTION_RETURN_MEDIA_TO_CALLING_APP = "return media to called app";
	public final static String EVENT_ACTION_OPEN_ALBUM = "album";
	public final static String EVENT_ACTION_SH_SELECT_ALL = "show_hide_select_all_albums";
	public final static String EVENT_ACTION_SH_SELECT_DONE = "show_hide_done";
	public final static String EVENT_ACTION_LAUNCH_GALLERY_SETTINGS = "launch_voice_tag_gallery_settings";
	public final static String EVENT_ACTION_LAUNCH_SHOW_HIDE = "launch_show_hide";
	public final static String EVENT_ACTION_EXIT_GALLERY = "exit_gallery";
	public final static String EVENT_ACTION_GALLERY_KEYWORD_TAG_HELP_MESSAGE = "gallery_help_message";
	public final static String EVENT_ACTION_GALLERY_DISPLAY_ALBUMS = "display_album";
	public final static String EVENT_ACTION_GALLERY_DISPLAY_ALBUMS_THUMBS = "display_album_thumbs";
	public final static String EVENT_ACTION_GALLERY_BACK = "back";
	public final static String EVENT_ACTION_SEARCH_ALL = "search_all";
	public final static String EVENT_ACTION_SEARCH_GEO_TAG = "search_geo_tag";
	public final static String EVENT_ACTION_SEARCH_DATE_TAG = "search_date_tag";
	public final static String EVENT_ACTION_SEARCH_KEYWORD_TAG = "search_keyword_tag";
	public final static String EVENT_ACTION_SEARCH_CLEAR_FIELD = "search_clear_text";
	public final static String EVENT_ACTION_SEARCH_MIC_INPUT = "search_mic_input";
	public final static String EVENT_ACTION_SEARCH_GO = "search_go";
	public final static String EVENT_ACTION_ADD_TAG_OK = "add_tag";
	public final static String EVENT_ACTION_TOGGLE_TO_SEARCH = "toggle_to_search";
	public final static String EVENT_ACTION_TOGGLE_TO_ADD_TAG = "toggle_to_add_tag";
	public final static String EVENT_ACTION_SELECT_MEDIA = "select_media";
	public final static String EVENT_ACTION_SELECT_TAG_MEDIA = "tag_media";
	public final static String EVENT_ACTION_TOGGLE_ALBUM = "toggle_album";
	public final static String EVENT_ACTION_GO_TO_FULL_VIEW = "go_to_full_view";
	public final static String EVENT_ACTION_SLIDE_SHOW = "album_slide_show";
	public final static String EVENT_ACTION_DETAIL = "media_detail";
	public final static String EVENT_ACTION_SHOW_HIDE_ALBUM = "show_hide_album";
	public final static String EVENT_ACTION_GALLERY_SETTINGS = "gallery_settings";
	public final static String EVENT_ACTION_EXIT = "exit";
	public final static String EVENT_ACTION_IMAGE_TAPPED = "media_tapped";
	public final static String EVENT_ACTION_IMAGE_SWIPE = "media_swipe";
	public final static String EVENT_ACTION_IMAGE_ZOOM_IN = "image_zoom_in";
	public final static String EVENT_ACTION_IMAGE_ZOOM_OUT = "image_zoom_out";
	public final static String EVENT_ACTION_SHOW_CALENDER_TO_TAG = "show_calender_to_date_tag";
	public final static String EVENT_ACTION_CALENDER_DATE_SELECTED_TAG = "calender_date_selected_to_tag";
	public final static String EVENT_ACTION_CALENDER_RESET_SELECTED_TAG = "calender_reset_date_tag";

	public final static String EVENT_ACTION_SHOW_ON_MAP = "show_on_map";
	public final static String EVENT_ACTION_FILTER = "filter";
	public final static String EVENT_ACTION_FILTER_SELECTED = "filter_selected";
	public final static String EVENT_ACTION_FILTER_SAVE = "filter_save_media";
	public final static String EVENT_ACTION_FILTER_CANCEL = "filter_cancel";

	public final static String EVENT_ACTION_IMPORT_MEDIA = "import_media";
	public final static String EVENT_ACTION_IMPORT = "import";

	//gellery_settings
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_TAGGING = "gallery_tagging";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_KEYWORD_TAG = "gallery_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_DATE_TAG = "gallery_date_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_LOCK_KEYWORD_TAG_PHOTOS = "gallery_lock_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_LOCK_DATE_TAG_PHOTOS = "gallery_lock_date_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_LISTEN_FOR_KEYWORD_TAG_MIC = "gallery_mic_listen_for_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_CONFIRM_KEYWORD_TAG = "gallery_confirm_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_CONFIRM_KEYWORD_TAG_ON_EXIT = "gallery_confirm_keyword_tag_on_exit_media";
	//
	//	public final static String EVENT_ACTION_SETTINGS_MARK_MEDIA_KEYWORD_TAGED = "gallery_mark_media_keyword_tag";
	//	public final static String EVENT_ACTION_SETTINGS_MARK_MEDIA_WO_KEYWORD_TAG = "gallery_mark_media_wo_keyword_tag";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_FORMER_KEYWORD_TAGS = "gallery_former_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_REMOVE_ALL_FORMER_KEYWORD_TAGS = "gallery_remove_all_former_keyword_tag_media";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_RESET_TO_DEFULT = "gallery_reset_to_default_voice_tag_settings";
	//	public final static String EVENT_ACTION_SETTINGS_GALLERY_RESET_TO_CUSTOM = "gallery_reset_to_custom_voice_tag_settings";
}
