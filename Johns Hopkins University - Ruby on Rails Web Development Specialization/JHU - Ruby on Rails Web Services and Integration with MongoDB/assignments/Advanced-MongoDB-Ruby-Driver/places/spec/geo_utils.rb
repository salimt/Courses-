module Geo_utils

	def	Geo_utils.distance(point1, point2)
		lat1 = point1[:coordinates][1] * Math::PI/180.0
		lon1 = point1[:coordinates][0] * Math::PI/180.0
		lat2 = point2[:coordinates][1] * Math::PI/180.0
		lon2 = point2[:coordinates][0] * Math::PI/180.0
		d = 6378.137 * Math.acos( Math.cos( lat1 ) * Math.cos( lat2 ) * Math.cos( lon2 - lon1 ) + Math.sin( lat1 ) * Math.sin( lat2 ) )
		return d * 1000
	end

	def Geo_utils.PointDistance(p1, p2) 
		lat1 = p1.latitude * Math::PI/180.0
		lon1 = p1.longitude * Math::PI/180.0
		lat2 = p2.latitude * Math::PI/180.0
		lon2 = p2.longitude * Math::PI/180.0
		d = 6378.137 * Math.acos( Math.cos( lat1 ) * Math.cos( lat2 ) * Math.cos( lon2 - lon1 ) + Math.sin( lat1 ) * Math.sin( lat2 ) )
		return d * 1000
	end
end
